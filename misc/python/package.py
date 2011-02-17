import os,shutil
from common import zipfolder,rmdir,rmfile

def prepare():
   rmdir(export_dir+target_name)
   os.chdir(source_dir)
   os.system('svn export . '+export_dir+target_name)
   os.chdir(export_dir)
   rmfile(target_name+'.zip')

def packageAll():
   os.chdir(export_dir)
   #os.system('xcopy /s/e/i/y '+source_dir+'\\tools\\ant\\apache-ant-1.8.2 '+target_name+'\\tools\\ant\\apache-ant-1.8.2')
   #os.system('xcopy /s/e/i/y '+source_dir+'\\tools\\maven\\apache-maven-3.0.2 '+target_name+'\\tools\\maven\\apache-maven-3.0.2')

   zipfolder(target_name, target_name+'.zip')


source_dir = os.path.abspath("F:\\springside\\springside4")
export_dir='C:\\'
springside_version='4.0.0'
target_name="springside-"+springside_version


#prepare()
packageAll()
