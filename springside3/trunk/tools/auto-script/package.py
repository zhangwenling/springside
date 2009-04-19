import os,shutil,zipfile
from os.path import join
from common import zipfolder,rmdir,zipfolder

def prepare():
   os.system('TortoiseProc.exe /command:export /path:"'+base_dir+'"')
   os.chdir(export_dir)
   rmdir(springside_dir)
   rmdir(springside_all_dir)

def packageSource():
   os.rename('springside3',springside_dir)
   zipfolder(springside_dir,springside_dir+'.zip')

def packageAll():
   os.rename(springside_dir,springside_all_dir)
   ## copy maven and repository 
   os.system('xcopy /s/e/i/y '+base_dir+'\\tools\\maven\\apache-maven-2.1.0 '+springside_all_dir+'\\tools\\maven\\apache-maven-2.1.0')
   os.system('xcopy /s/e/i/h %USERPROFILE%\\.m2\\repository '+springside_all_dir + '\\tools\\maven\\central-repository')

   ## copy tomcat 
   os.chdir(base_dir+'\\servers\\tomcat\\apache-tomcat-6.0.18')
   emptydir('temp')
   emptydir('work')
   emptydir('logs')
   rmdir('webapps/mini-web')
   rmdir('webapps/mini-service')
   rmdir('webapps/showcase')

   os.chdir(export_dir)
   os.system('xcopy /s/e/i/y '+base_dir+'\\servers\\tomcat\\apache-tomcat-6.0.18 '+springside_all_dir+'\\servers\\tomcat\\apache-tomcat-6.0.18')

   zipfolder(springside_all_dir,springside_all_dir+'.zip')

base_dir = os.path.abspath("../../")
export_dir='C:\\'
springside_version='3.1.3'
springside_dir="springside-"+springside_version
springside_all_dir="springside-"+springside_version+"-all-in-one"

prepare()
packageSource()
packageAll()
