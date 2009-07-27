${pojo.getPackageDeclaration()}
<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>
<#assign entityName = declarationName?uncap_first>
public class ${declarationName}DaoTest extends ${pojo.importType("org.springside.modules.test.spring.SpringTxTestCase")}  {
	@${pojo.importType("org.springframework.beans.factory.annotation.Autowired")}
	private ${declarationName}rDao entityDao;

	@${pojo.importType("org.junit.Test")}
	public void crudEntity() {
		//new entity and save it. 
		//new entity and set its properties here, or get it  from a DataGenerator.
		${declarationName} entity = new ${declarationName}(); 
		entityrDao.save(entity);
		flush();

		entity = entityrDao.get(entity.getId());
		
		//modify entity.
		//modify som properties here.
		userDao.save(entity);
		flush();
		
		//delete entity.
		userDao.delete(entity.getId());
		flush();
	}
}
</#assign>

${pojo.generateImports()}
import ${pojo.getQualifiedDeclarationName()};

${classbody}
