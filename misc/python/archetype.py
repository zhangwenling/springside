import os,shutil
from common import rmdir,rmfile,move,replaceinfile

def prepare():
    move(base_dir+'\\examples\\mini-service\\lib',base_dir+'\\examples\\mini-service\\target\\tmp\\lib')
    move(base_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\lib',base_dir+'\\examples\\mini-service\\target\\tmp\\WEB-INF-lib')
    move(base_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\classes',base_dir+'\\examples\\mini-service\\target\\tmp\\WEB-INF-classes')
   
    move(base_dir+'\\examples\\mini-web\\lib',base_dir+'\\examples\\mini-web\\target\\tmp\\lib')
    move(base_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\lib',base_dir+'\\examples\\mini-web\\target\\tmp\\WEB-INF-lib')
    move(base_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\classes',base_dir+'\\examples\\mini-web\\target\\tmp\\WEB-INF-classes')

    move(base_dir+'\\examples\\mini-service\\webapp\\wsdl\\mini-service.wsdl',base_dir+'\\examples\\mini-service\\target\\tmp\\mini-service.wsdl')
    move(base_dir+'\\examples\\mini-service\\src\\test\\soapui\\mini-service-soapui-project.xml',base_dir+'\\examples\\mini-service\\target\\tmp\\mini-service-soapui-project.xml')

    shutil.copyfile(base_dir+"\\modules\\parent\\pom.xml",base_dir+"\\examples\\mini-service\\pom-parent.xml")
    shutil.copyfile(base_dir+"\\modules\\parent\\pom.xml",base_dir+"\\examples\\mini-web\\pom-parent.xml")

    print 'prepared example projects.'

def createArchetypes():
    os.chdir(base_dir+'\\examples\\mini-service')
    os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniservice')

    os.chdir(base_dir+'\\examples\\mini-web')
    os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniweb')
    
    os.system('xcopy /s/e/i/y '+base_dir+'\\examples\\mini-service\\target\\generated-sources\\archetype\\src\\main\\resources\\archetype-resources '+base_dir+'\\tools\\generator\\maven-archetypes\\service-archetype\\src\\main\\resources\\archetype-resources')
    os.system('xcopy /s/e/i/y '+base_dir+'\\examples\\mini-web\\target\\generated-sources\\archetype\\src\\main\\resources\\archetype-resources '+base_dir+'\\tools\\generator\\maven-archetypes\\web-archetype\\src\\main\\resources\\archetype-resources')

    print 'created archetypes.'

def modifyArchetypes():
    commonModifyArchetype(base_dir+'/tools/generator/maven-archetypes/service-archetype/src/main/resources/archetype-resources')
    commonModifyArchetype(base_dir+'/tools/generator/maven-archetypes/web-archetype/src/main/resources/archetype-resources')

    os.chdir(base_dir+'/tools/generator/maven-archetypes/service-archetype/src/main/resources/archetype-resources')
    replaceinfile('bin/ws-bin/build-client.bat','mini-service','${artifactId}')
    replaceinfile('bin/ws-bin/save-wsdl.bat','mini-service','${artifactId}')
    replaceinfile('bin/ws-bin/build-client-binding.xml','http://miniservice.examples.springside.org','${webservice-namespace}')
    replaceinfile('src/main/java/ws/Constants.java','http://miniservice.examples.springside.org','${webservice-namespace}')

    print 'modified archetypes.'
    
def commonModifyArchetype(path):
    os.chdir(path)
    replaceinfile('pom.xml','springside-parent','${artifactId}-parent')
    replaceinfile('pom.xml','../../modules/parent/pom.xml','pom-parent.xml')
    replaceinfile('pom.xml','mini-service','${artifactId}')
    replaceinfile('pom.xml','mini-web','${artifactId}')
    replaceinfile('pom.xml','springside-miniweb','${artifactId}')
    replaceinfile('pom.xml','springside-miniservice','${artifactId}')
    replaceinfile('pom.xml','<artifactId>\${artifactId}-parent</artifactId>\n		<groupId>org.springside</groupId>','<artifactId>${artifactId}-parent</artifactId>\n		<groupId>${groupId}</groupId>')
    replaceinfile('pom.xml','<version>'+springside_version+'</version>\n		<relativePath>pom-parent.xml</relativePath>','<version>${version}</version>\n		<relativePath>pom-parent.xml</relativePath>')		
	
    replaceinfile('pom-parent.xml','<modelVersion>4.0.0</modelVersion>\n	<groupId>org.springside</groupId>','<modelVersion>4.0.0</modelVersion>\n	<groupId>${groupId}</groupId>')
    replaceinfile('pom-parent.xml','springside-parent','${artifactId}-parent')
    replaceinfile('pom-parent.xml','<springside.version>\${version}</springside.version>','<springside.version>'+springside_version+'</springside.version>')

    replaceinfile('.settings/org.eclipse.wst.common.component','mini-service','${artifactId}')
    replaceinfile('.settings/org.eclipse.wst.common.component','mini-web','${artifactId}')
    
    replaceinfile('src/main/resources/application.properties','springside-miniservice','${artifactId}')
    replaceinfile('src/main/resources/application.properties','springside-miniweb','${artifactId}')

    replaceinfile('.classpath','	<classpathentry combineaccessrules="false" kind="src" path="/springside3-core"/>\n','')
    replaceinfile('.project','		<project>springside3-core</project>\n','')
    replaceinfile('.settings/org.eclipse.wst.common.component','<dependent-module archiveName="springside3-core.jar" deploy-path="/WEB-INF/lib" handle="module:/resource/springside3-core/springside3-core">\n<dependency-type>uses</dependency-type>\n</dependent-module>','')

def clean():
    rmfile(base_dir+'\\examples\\mini-service\\pom-parent.xml')
    rmfile(base_dir+'\\examples\\mini-web\\pom-parent.xml')

    rmdir(base_dir+'\\examples\\mini-service\\target\\generated-sources')
    rmdir(base_dir+'\\examples\\mini-web\\target\\generated-sources')

    move(base_dir+'\\examples\\mini-service\\target\\tmp\\lib', base_dir+'\\examples\\mini-service\\lib')
    move(base_dir+'\\examples\\mini-service\\target\\tmp\\WEB-INF-lib', base_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\lib')
    move(base_dir+'\\examples\\mini-service\\target\\tmp\\WEB-INF-classes', base_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\classes')

    move(base_dir+'\\examples\\mini-web\\target\\tmp\\lib', base_dir+'\\examples\\mini-web\\lib')
    move(base_dir+'\\examples\\mini-web\\target\\tmp\\WEB-INF-lib', base_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\lib')
    move(base_dir+'\\examples\\mini-web\\target\\tmp\\WEB-INF-classes', base_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\classes')

    move(base_dir+'\\examples\\mini-service\\target\\tmp\\mini-service.wsdl',base_dir+'\\examples\\mini-service\\webapp\\wsdl\\mini-service.wsdl')
    move(base_dir+'\\examples\\mini-service\\target\\tmp\\mini-service-soapui-project.xml', base_dir+'\\examples\\mini-service\\src\\test\\soapui\\mini-service-soapui-project.xml')
 
    print 'cleaned temp files.'

springside_version='3.1.4'
base_dir = os.path.abspath("../../")

prepare()
createArchetypes()
modifyArchetypes()
clean()
