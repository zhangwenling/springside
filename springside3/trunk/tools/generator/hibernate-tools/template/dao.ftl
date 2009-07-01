package ${basePackage}.dao;
<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>
public class ${declarationName}Dao extends HibernateDao<${declarationName}, ${PK}> {
}
</#assign>
${pojo.generateImports()}
import org.springside.modules.orm.hibernate.HibernateDao;
import ${pojo.getQualifiedDeclarationName()};

${classbody}
