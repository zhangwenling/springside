${pojo.getPackageDeclaration()}
<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>
<#assign entityName = declarationName?uncap_first>
public class ${declarationName}ManagerTest extends ${pojo.importType("org.junit.Assert")} {
	prvate ${declarationName}Manager ${entityName}Manager = new ${declarationName}Manager();
	private ${declarationName}Dao ${entityName}Dao = null;
	
	@${pojo.importType("org.junit.Before")}
	public void setUp() {
		${entityName}Dao = ${pojo.importType("org.easymock.classextension.EasyMock")}.createMock(${declarationName}Dao.class);
		${pojo.importType("org.springside.modules.utils.ReflectionUtils")}.setFieldValue(${declarationName}Manager, "${entityName}Dao", ${entityName}Dao);
	}

	@${pojo.importType("org.junit.After")}
	public void tearDown() {
		EasyMock.verify(${entityName}Dao);
	}
}
</#assign>

${pojo.generateImports()}
import ${pojo.getQualifiedDeclarationName()};

${classbody}
