import os,shutil,zipfile
from os.path import join
from datetime import date
from time import time

def zipfolder(foldername,filename):
    empty_dirs=[]
    zip=zipfile.ZipFile(filename,'w',zipfile.ZIP_DEFLATED)
    for root,dirs,files in os.walk(foldername):
        empty_dirs.extend([dir for dir in dirs if os.listdir(join(root,dir))==[]])
        for filename in files:
            zip.write(join(root,filename).encode("gbk"))
    for dir in empty_dirs:
        zif=zipfile.ZipInfo(join(root,dir).encode("gbk"+"/"))
        zip.writestr(zif,"")
    zip.close()

def rmdir(path):
    if os.path.exists(path):
        shutil.rmtree(path)
def emptydir(path):
    rmdir(path)
    os.mkdir(path)

def prepare():
   os.system('TortoiseProc.exe /command:export /path:"'+home_dir+'"')
   os.chdir(export_dir)
   rmdir(springside_dir)
   rmdir(springside_all_dir)

def release_source():
   os.rename('springside3',springside_dir)
   zipfolder(springside_dir,springside_dir+'.zip')

def release_all():
   os.rename(springside_dir,springside_all_dir)
   ##copy maven and repository 
   os.system('xcopy /s/e/i/y '+home_dir+'\\tools\\maven\\apache-maven-2.1.0 '+springside_all_dir+'\\tools\\maven\\apache-maven-2.1.0')
   os.system('xcopy /s/e/i/h %USERPROFILE%\\.m2\\repository '+springside_all_dir + '\\tools\\maven\\central-repository')

   ##copy tomcat 
   os.chdir(home_dir+'\\servers\\tomcat\\apache-tomcat-6.0.18')
   emptydir('temp')
   emptydir('work')
   emptydir('logs')
   rmdir('webapps/mini-web')
   rmdir('webapps/mini-service')
   rmdir('webapps/showcase')

   os.chdir(export_dir)
   os.system('xcopy /s/e/i/y '+home_dir+'\\servers\\tomcat\\apache-tomcat-6.0.18 '+springside_all_dir+'\\servers\\tomcat\\apache-tomcat-6.0.18')

   zipfolder(springside_all_dir,springside_all_dir+'.zip')


home_dir = os.path.abspath("../../")
export_dir='C:\\'
springside_version='3.1.3'
springside_dir="springside-"+springside_version
springside_all_dir="springside-"+springside_version+"-all-in-one"

prepare()
release_source()
release_all()
