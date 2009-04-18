import os,shutil,re

def prepare():
    print 'preparing example projects.'
    rmdir(home_dir+'\\examples\\mini-service\\lib')
    rmdir(home_dir+'\\examples\\mini-service\\target')
    rmdir(home_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\lib')
    rmdir(home_dir+'\\examples\\mini-service\\webapp\\WEB-INF\\classes')

    rmdir(home_dir+'\\examples\\mini-web\\lib')
    rmdir(home_dir+'\\examples\\mini-web\\target')
    rmdir(home_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\lib')
    rmdir(home_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\classes')

    rmfile(home_dir+'\\examples\\mini-service\\webapp\\wsdl\\mini-service.wsdl')
    rmfile(home_dir+'\\examples\\mini-service\\src\\test\\soapui\\mini-service-soapui-project.xml')

    shutil.copyfile(home_dir+"\\modules\\parent\\pom.xml",home_dir+"\\examples\\mini-service\\pom-parent.xml")
    shutil.copyfile(home_dir+"\\modules\\parent\\pom.xml",home_dir+"\\examples\\mini-web\\pom-parent.xml")

def createArchetype():
    print 'creating archetypes.'
    
    os.chdir(home_dir+'\\examples\\mini-service')
    os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniservice')

    os.chdir(home_dir+'\\examples\\mini-web')
    os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniweb')

def copyArchetype():
    print 'copying archetypes.'
    os.system('xcopy /s/e/i/y '+home_dir+'\\examples\\mini-service\\target\\generated-sources\\archetype\\src\\main\\resources\\archetype-resources '+home_dir+'\\tools\\generator\\maven-archetypes\\service-archetype\\src\\main\\resources\\archetype-resources')
    os.system('xcopy /s/e/i/y '+home_dir+'\\examples\\mini-web\\target\\generated-sources\\archetype\\src\\main\\resources\\archetype-resources '+home_dir+'\\tools\\generator\\maven-archetypes\\web-archetype\\src\\main\\resources\\archetype-resources')

def modifyArchetype():
    commonModifyArchetype(home_dir+'/tools/generator/maven-archetypes/service-archetype/src/main/resources/archetype-resources')
    commonModifyArchetype(home_dir+'/tools/generator/maven-archetypes/web-archetype/src/main/resources/archetype-resources')

    os.chdir(home_dir+'/tools/generator/maven-archetypes/service-archetype/src/main/resources/archetype-resources')
    replaceinfile('bin/ws-bin/build-client.bat','mini-service','${artifactId}')
    replaceinfile('bin/ws-bin/save-wsdl.bat','mini-service','${artifactId}')
    replaceinfile('bin/ws-bin/build-client-binding.xml','http://miniservice.examples.springside.org','${webservice-namespace}')
    replaceinfile('src/main/java/ws/Constants.java','http://miniservice.examples.springside.org','${webservice-namespace}')
    
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


def rmdir(path):
    if os.path.exists(path):
        shutil.rmtree(path)

def rmfile(path):
    if os.path.exists(path):
        os.remove(path)

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
createArchetype()
copyArchetype()
modifyArchetype()
rmfile(home_dir+'\\examples\\mini-service\\pom-parent.xml')
rmfile(home_dir+'\\examples\\mini-web\\pom-parent.xml')
