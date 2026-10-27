package com.example.demo.mapper;

import com.example.demo.model.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    // column --> property mapping
    @Results(id="memberResults", value={
            @Result(column="name", property="name"),
            @Result(column="email", property="email")
    })
    @Select("SELECT * FROM member")
    List<Member> selectAll();

    @ResultMap("memberResults")
    @Select("SELECT * FROM member WHERE id = #{id}")
    Optional<Member> selectById(@Param("id") Long id);

    @Select("SELECT * FROM member WHERE age >= #{age}")
    @ResultMap("memberResults")
    List<Member> selectByAgeGreaterThanEqual(@Param("age") int age);

    @Select("SELECT * FROM member ORDER BY age")
    List<Member> selectAllOrderByAge();

    @Select("SELECT * FROM member ORDER BY age DESC")
    List<Member> selectAllOrderByAgeDesc();

    @Select("SELECT * FROM member ORDER BY ${order} ${dir}")
    List<Member> selectAllOrderBy(@Param("order") String order, @Param("dir") String dir);

    @Insert("INSERT INTO member(name, email, age) VALUES(#{member.name}, #{member.email}, #{member.age})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(@Param("member") Member member);

    @Update("UPDATE member SET name=#{member.name}, email=#{member.email}, age=#{member.age} WHERE id=#{member.id}")
    int update(@Param("member") Member member);

    @Update("UPDATE member SET email=#{email} WHERE id=#{id}")
    int updateEmail(@Param("id") Long id, @Param("email") String email);

    @Delete("DELETE FROM member WHERE id=#{id}")
    int deleteById(@Param("id") Long id);

}
