${pojo.getPackageDeclaration()}
<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>
@${pojo.importType("org.springframework.stereotype.Repository")}
public class ${declarationName}Dao extends HibernateDao<${declarationName}, ${pojo.getJavaTypeName(clazz.identifierProperty, true)}> {
}
</#assign>
${pojo.generateImports()}
import org.springside.modules.orm.hibernate.HibernateDao;
import ${pojo.getQualifiedDeclarationName()};

${classbody}
