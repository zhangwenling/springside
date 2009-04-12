import os
import shutil

ss_dir = 'F:\springside\springside3'

def rmdir(path):
    if os.path.exists(path):
        shutil.rmtree(path)

def rmfile(path):
    if os.path.exists(path):
        os.remove(path)

rmdir(ss_dir+'\examples\mini-service\lib')
rmdir(ss_dir+'\examples\mini-service\target')
rmdir(ss_dir+'\examples\mini-service\webapp\WEB-INF\lib')
rmdir(ss_dir+'\examples\mini-service\webapp\WEB-INF\classes')

rmdir(ss_dir+'\examples\mini-web\lib')
rmdir(ss_dir+'\examples\mini-web\target')
rmdir(ss_dir+'\examples\mini-web\webapp\WEB-INF\lib')
rmdir(ss_dir+'\examples\mini-web\webapp\WEB-INF\classes')

rmfile(ss_dir+'\examples\mini-service\webapp\wsdl\mini-service.wsdl')
rmfile(ss_dir+'\examples\mini-service\src\test\soapui\mini-service-soapui-project.xml')

shutil.copyfile(ss_dir+"\modules\parent\pom.xml",ss_dir+"\examples\mini-service\pom-parent.xml")
shutil.copyfile(ss_dir+"\modules\parent\pom.xml",ss_dir+"\examples\mini-web\pom-parent.xml")

os.chdir(ss_dir+'\examples\mini-service')
os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniservice')

os.chdir(ss_dir+'\examples\mini-web')
os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniweb')
