<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.mapper.${mouldName}.${className}Mapper">

	<sql id="columns">
		<#list cloums as c>
			<#if c_has_next>
				a.${ c.columnNameL} AS "${ c.columnName}",
			</#if>
			<#if !c_has_next>
				a.${ c.columnNameL} AS "${ c.columnName}"
			</#if>
		</#list>
	</sql>
	
	 <!-- 查询条件 -->
    <sql id="selectPageParmas">
        <where>
        	1=1
        	<if test="id != null and id != '' ">
            	<![CDATA[and a.id =  ${r"#{id}"}]]>
            </if>
        </where>
    </sql>
    
    <select id="get" parameterType="com.model.${mouldName}.${className}" resultType="com.model.${mouldName}.${className}">
        select 
			<include refid="columns"/>
        from ${tableName} a
        <include refid="selectPageParmas" />
    </select>
    
	<select id="getCount" parameterType="com.model.${mouldName}.${className}" resultType="Long">
        select count(id)
        from ${tableName} a
        <include refid="selectPageParmas" />
    </select>
	
	<select id="getAllList" resultType="com.model.${mouldName}.${className}" parameterType="com.model.${mouldName}.${className}" >
		<if test="dbName == 'mysql'">
			select
	    		<include refid="columns"/>
	    	from ${tableName} a
	    	<include refid="selectPageParmas" />
		</if>
		<if test="dbName == 'mssql'">
			 SELECT
			      <include refid="columns"/>
			  FROM ${tableName} a
			     <include refid="selectPageParmas" />
		</if>
        <if test="dbName == 'oracle'">
            SELECT
            <include refid="columns"/>
            FROM ${tableName} a
            <include refid="selectPageParmas" />
        </if>
	 </select>
	 
	<select id="getListByPage" resultType="com.model.${mouldName}.${className}" parameterType="com.model.${mouldName}.${className}" >
	    <!-- 数据库类型不一样，sql不一样 -->
		<if test="dbName == 'mysql'">
			select
	    		<include refid="columns"/>
	    	from ${tableName} a
	    	<include refid="selectPageParmas" />
	        LIMIT ${r"#{start}"},${r"#{limit}"}
		</if>
		<if test="dbName == 'mssql'">
			 SELECT
			      TOP ${r"${limit}"} *
			  FROM
			      (
			          SELECT
			              ROW_NUMBER () OVER (ORDER BY id ASC) RowNumber ,<include refid="columns"/>
			          FROM
			             ${tableName} a
			              <include refid="selectPageParmas" />
			      ) A
			 WHERE
			     A.RowNumber > (${r"#{start}"} - 1) * ${r"#{limit}"}
		</if>
        <if test="dbName == 'oracle'">
            SELECT
            <include refid="columns"/>
            from ${tableName} a
            <include refid="selectPageParmas" />
            and ROWNUM BETWEEN (${r"#{start}"}+1) AND (${r"#{start}"}+${r"#{limit}"})
        </if>
	 </select>
	 
  	<insert id="insert">
		INSERT INTO ${tableName}(
		<#list cloums as c>
			<#if c.columnNameL == "id">
			</#if>
			<#if c.columnNameL != "id">
				<#if c_has_next>
					${ c.columnNameL},
				</#if>
				<#if !c_has_next>
					${ c.columnNameL}
				</#if>
			</#if>
		</#list>
		) VALUES (
		<#list cloums as c>
			<#if c.columnNameL == "id">
			</#if>
			<#if c.columnNameL != "id">
				<#if c_has_next>
					${r"#{"}${ c.columnName}${r"}"},
				</#if>
				<#if !c_has_next>
					${r"#{"}${ c.columnName}${r"}"}
				</#if>
			</#if>
		</#list>
		)
	</insert>
  
  	<update id="update">
		UPDATE ${tableName} SET 	
			<#list cloums as c>
				<#if c.columnNameL == "id">
				</#if>
				<#if c.columnNameL != "id">
					<#if c_has_next>
						<if test="${ c.columnName} != null and ${ c.columnName} != ''">${ c.columnNameL} = ${r"#{"}${ c.columnName}${r"}"},</if>
					</#if>
					<#if !c_has_next>
						<if test="${ c.columnName} != null and ${ c.columnName} != ''">${ c.columnNameL} = ${r"#{"}${ c.columnName}${r"}"}</if>
					</#if>
				</#if>
			</#list>
		WHERE id =  ${r"#{id}"}
	</update>
	
	  <delete id="delete" parameterType="java.lang.Integer" >
	    delete from ${tableName}
	    where id = ${r"#{id,jdbcType=INTEGER}"}
	  </delete>
</mapper>
