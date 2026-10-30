package com.example.demo.tool;

import com.example.demo.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductOrderTool {

    @Autowired
    private ProductOrderRepository repository;

    @Tool(description = "주문목록(주문번호, 상품이름, 배송주소, 배송상태)을 조회합니다.")
    public String getProductOrders(ToolContext toolContext) {
        String memberName = (String) toolContext.getContext().get("username");
        log.info("주문목록조회 도구 호출");
        var orders = repository.findByMemberName(memberName);
        if (orders.isEmpty()) {
            return "주문목록이 없습니다";
        } else {
            StringBuilder builder = new StringBuilder("주문목록은 다음과 같아요.");
            for (var order : orders) {
                builder.append("주문번호: ").append(order.getOrderNumber());
                builder.append(", 상품이름: ").append(order.getProductName());
                builder.append(", 배송주소: ").append(order.getShippingAddress());
                builder.append(", 배송상태: ").append(order.getShippingStatus());
                builder.append("\n");
            }
            return builder.toString();
        }
    }

    @Tool(description = "주문번호를 사용하여 상품주문을 취소합니다.")
    public String cancelProductOrder(
            ToolContext toolContext,
            @ToolParam(description = "주문번호") String orderNumber) {

        log.info("주문취소 도구 호출");
        String memberName = (String) toolContext.getContext().get("username");
        var order = repository.findByOrderNumberAndMemberName(orderNumber, memberName);
        if (order.isPresent()) {
            if ("배송중".equals(order.get().getShippingStatus())) {
                return "배송중인 상품은 취소할 수 없습니다.";
            } else {
                repository.delete(order.get());
                return "주문이 취소되었습니다.";
            }
        } else {
            return "없는 주문번호입니다.";
        }
    }





}
