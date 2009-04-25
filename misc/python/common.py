import os,shutil,re,zipfile
from os.path import join

def rmdir(path):
    if os.path.exists(path):
        shutil.rmtree(path)

def rmfile(path):
    if os.path.exists(path):
        os.remove(path)
        
def emptydir(path):
    rmdir(path)
    os.mkdir(path)

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
