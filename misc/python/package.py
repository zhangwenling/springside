import os,shutil
from common import zipfolder,rmdir,zipfolder,emptydir,rmfile



def prepare():
   rmdir(export_dir+'springside3')
   os.chdir(base_dir)
   os.system('svn export . '+export_dir+'springside3')
   os.chdir(export_dir)
   rmfile(springside_dir+'-src.zip')
   rmfile(springside_dir+'-all-in-one.zip')

def packageSource():
   os.rename('springside3',springside_dir)
   zipfolder(springside_dir,springside_dir+'-src.zip')

def packageAll():
   ## copy maven and repository
   os.system('xcopy /s/e/i/y '+base_dir+'\\tools\\ant\\apache-ant-1.7.1 '+springside_dir+'\\tools\\ant\\apache-ant-1.7.1')
   os.system('xcopy /s/e/i/y '+base_dir+'\\tools\\maven\\apache-maven-2.2.1 '+springside_dir+'\\tools\\maven\\apache-maven-2.2.1')
   os.system('xcopy /s/e/i/h %USERPROFILE%\\.m2\\repository '+export_dir+springside_dir + '\\tools\\maven\\central-repository')
   os.chdir(export_dir)
   zipfolder(springside_dir,"springside-"+springside_version+'-all-in-one.zip')
   
def clean():
   rmdir(springside_dir)

base_dir = os.path.abspath("../../../")
export_dir='C:\\'
springside_version='3.3.3'
springside_dir="springside-"+springside_version


prepare()
packageSource()
packageAll()
##clean()
##   move .m2 to nuxus central   
##   os.chdir("F:\\springside\\springside3\\tools\\misc\\nexus-webapp-1.4.1-bundle\\nexus-webapp-1.4.1\\bin\\jsw\\windows-x86-32")
##   os.system('Nexus.bat')
##   os.chdir(base_dir)
##   os.system('quick-start.bat')
##   delete .m2 springside
