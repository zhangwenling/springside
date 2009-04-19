import os,shutil,re

def prepare():
    rmdir(home_dir+'\\examples\\mini-service\\target')
    rmdir(home_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\classes')
    move(home_dir+'\\examples\\mini-service\\lib',home_dir+'\\examples\\mini-service\\target\\tmp\\lib')
    move(home_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\lib',home_dir+'\\examples\\mini-service\\target\\tmp\\WEB-INF-lib')
   
    rmdir(home_dir+'\\examples\\mini-web\\target')
    rmdir(home_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\classes')
    move(home_dir+'\\examples\\mini-web\\lib',home_dir+'\\examples\\mini-web\\target\\tmp\\lib')
    move(home_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\lib',home_dir+'\\examples\\mini-web\\target\\tmp\\WEB-INF-lib')

    move(home_dir+'\\examples\\mini-service\\webapp\\wsdl\\mini-service.wsdl',home_dir+'\\examples\\mini-web\\target\\tmp\\mini-service.wsdl')
    move(home_dir+'\\examples\\mini-service\\src\\test\\soapui\\mini-service-soapui-project.xml',home_dir+'\\examples\\mini-web\\target\\tmp\\mini-service-soapui-project.xml')

    shutil.copyfile(home_dir+"\\modules\\parent\\pom.xml",home_dir+"\\examples\\mini-service\\pom-parent.xml")
    shutil.copyfile(home_dir+"\\modules\\parent\\pom.xml",home_dir+"\\examples\\mini-web\\pom-parent.xml")

    print 'prepared example projects.'

def createArchetypes():
    os.chdir(home_dir+'\\examples\\mini-service')
    os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniservice')

    os.chdir(home_dir+'\\examples\\mini-web')
    os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniweb')
    print 'created archetypes.'

def copyArchetypes():  
    os.system('xcopy /s/e/i/y '+home_dir+'\\examples\\mini-service\\target\\tmp\\generated-sources\\archetype\\src\\main\\resources\\archetype-resources '+home_dir+'\\tools\\generator\\maven-archetypes\\service-archetype\\src\\main\\resources\\archetype-resources')
    os.system('xcopy /s/e/i/y '+home_dir+'\\examples\\mini-web\\target\\tmp\\generated-sources\\archetype\\src\\main\\resources\\archetype-resources '+home_dir+'\\tools\\generator\\maven-archetypes\\web-archetype\\src\\main\\resources\\archetype-resources')
    print 'copied archetypes.'

def modifyArchetypes():
    commonModifyArchetype(home_dir+'/tools/generator/maven-archetypes/service-archetype/src/main/resources/archetype-resources')
    commonModifyArchetype(home_dir+'/tools/generator/maven-archetypes/web-archetype/src/main/resources/archetype-resources')

    os.chdir(home_dir+'/tools/generator/maven-archetypes/service-archetype/src/main/resources/archetype-resources')
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
    replaceinfile('pom.xml','<artifactId>\${artifactId}-parent</artifactId>\n		<groupId>org.springside</groupId>','<artifactId>${artifactId}-parent</artifactId>\n		<groupId>${groupId}</groupId>')
    replaceinfile('pom.xml','<version>'+springside_version+'</version>\n		<relativePath>pom-parent.xml</relativePath>','<version>${version}</version>\n		<relativePath>pom-parent.xml</relativePath>')		
	
    replaceinfile('pom-parent.xml','<modelVersion>4.0.0</modelVersion>\n	<groupId>org.springside</groupId>','<modelVersion>4.0.0</modelVersion>\n	<groupId>${groupId}</groupId>')
    replaceinfile('pom-parent.xml','springside-parent','${artifactId}-parent')
    replaceinfile('pom-parent.xml','<springside.version>\${version}</springside.version>','<springside.version>'+springside_version+'</springside.version>')

    replaceinfile('.settings/org.eclipse.wst.common.component','mini-service','${artifactId}')

    replaceinfile('.classpath','	<classpathentry combineaccessrules="false" kind="src" path="/springside3-core"/>\n','')
    replaceinfile('.project','		<project>springside3-core</project>\n','')
    replaceinfile('.settings/org.eclipse.wst.common.component','<dependent-module archiveName="springside3-core.jar" deploy-path="/WEB-INF/lib" handle="module:/resource/springside3-core/springside3-core">\n<dependency-type>uses</dependency-type>\n</dependent-module>','')

def clean():
    rmfile(home_dir+'\\examples\\mini-service\\pom-parent.xml')
    rmfile(home_dir+'\\examples\\mini-web\\pom-parent.xml')

    rmdir(home_dir+'\\examples\\mini-service\\target\\tmp\\generated-sources')
    rmdir(home_dir+'\\examples\\mini-web\\target\\tmp\\generated-sources')

    move(home_dir+'\\examples\\mini-service\\target\\tmp\\lib', home_dir+'\\examples\\mini-service\\lib')
    move(home_dir+'\\examples\\mini-service\\target\\tmp\\WEB-INF-lib', home_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\lib')

    move(home_dir+'\\examples\\mini-web\\target\\tmp\\lib', home_dir+'\\examples\\mini-web\\lib')
    move(home_dir+'\\examples\\mini-web\\target\\tmp\\WEB-INF-lib', home_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\lib')

    move(home_dir+'\\examples\\mini-web\\target\\tmp\\mini-service.wsdl', home_dir+'\\examples\\mini-service\\webapp\\wsdl\\mini-service.wsdl')
    move(home_dir+'\\examples\\mini-web\\target\\tmp\\mini-service-soapui-project.xml', home_dir+'\\examples\\mini-service\\src\\test\\soapui\\mini-service-soapui-project.xml')
 
    print 'cleaned temp files.'

def rmdir(path):
    if os.path.exists(path):
        shutil.rmtree(path)

def rmfile(path):
    if os.path.exists(path):
        os.remove(path)
        
def move(src,dest):
    if os.path.exists(src):
        shutil.move(src,dest)

def replaceinfile(path,src,target):
    srcre = re.compile(src,re.DOTALL);
    f = open(path,'r')
    readlines = f.read()
    re.sub
    writelines = re.sub(srcre,target,readlines,0)
    f.close()
    f = open(path,'w')
    f.write(writelines)
    f.close()
    
        
springside_version='3.1.3'
home_dir = os.path.abspath("../../")
prepare()
createArchetypes()
copyArchetypes()
modifyArchetypes()
clean()
