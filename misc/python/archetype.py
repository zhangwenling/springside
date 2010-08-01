import os,shutil
from common import rmdir,rmfile,move,replaceinfile

def prepare():
    move(base_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\classes',base_dir+'\\examples\\mini-web\\target\\tmp\\WEB-INF-classes')
    rmdir(base_dir+'\\examples\\mini-web\\logs\\')
    rmdir(base_dir+'\\examples\\mini-web\\bin\\hibernate\\logs\\')
    rmdir(base_dir+'\\examples\\mini-web\\bin\\hibernate\\generated\\')
    rmfile(base_dir+'\\examples\\mini-web\\.project')
    rmfile(base_dir+'\\examples\\mini-web\\.classpath')
    rmfile(base_dir+'\\examples\\mini-web\\.settings\\org.eclipse.jdt.core.prefs')
    rmfile(base_dir+'\\examples\\mini-web\\.settings\\org.eclipse.wst.common.component')
    rmfile(base_dir+'\\examples\\mini-web\\.settings\\org.eclipse.wst.common.project.facet.core.xml')
    print 'prepared example projects.'

def createArchetypes():
    os.chdir(base_dir+'\\examples\\mini-web')
    os.system('mvn archetype:create-from-project -DpackageName=org.springside.examples.miniweb')
    os.system('xcopy /s/e/i/y '+base_dir+'\\examples\\mini-web\\target\\generated-sources\\archetype\\src\\main\\resources\\archetype-resources '+base_dir+'\\tools\\maven\\archetype\\src\\main\\resources\\archetype-resources')
    print 'created archetypes.'

def modifyArchetypes():
    os.chdir(base_dir+'\\tools\\maven\\archetype\\src\\main\\resources\\archetype-resources')
    replaceinfile('pom.xml','Springside3 Mini-Web Example','${artifactId}')
    replaceinfile('src\\main\\resources\\application.properties','miniweb','${artifactId}')
    replaceinfile('bin\\hibernate\\hibernate.cfg.xml','miniweb','${artifactId}')
    print 'modified archetypes.'    

def clean():
    rmdir(base_dir+'\\examples\\mini-web\\target\\generated-sources')

    move(base_dir+'\\examples\\mini-web\\target\\tmp\\WEB-INF-classes', base_dir+'\\examples\\mini-web\\webapp\\WEB-INF\\classes')
    os.chdir(base_dir+'\\examples\\mini-web')
    os.system('mvn eclipse:clean eclipse:eclipse -Declipse.workspace=F:\workspace')
    
    print 'cleaned temp files.'

base_dir = os.path.abspath("../../../")

prepare()
createArchetypes()
modifyArchetypes()
clean()
