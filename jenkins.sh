echo **************************欢迎使用快速开发后端模板**************************
version="1.0.1"
dockercompose="/root/docker-compose.yml"
repository="registry.cn-hongkong.aliyuncs.com/souther/generalproject"
remoteProjectDir="/dockerconf/project/work/generalproject"
echo **************************配置信息**************************
echo 版本号: ${version}
echo docker-compose配置文件位置: ${dockercompose}
echo 镜像仓库名称: ${repository}
echo 远程服务器项目根目录: ${remoteProjectDir}

echo 远程控制服务器开始啦!!!
cd ${remoteProjectDir}
if [ $? -eq 0 ]; then
   echo "删除全部无效镜像"
   docker rmi -f $(docker images | grep "none" | awk '{print $3}')
  # echo "删除旧的相关镜像容器"
  # docker rm -f `docker images -q --filter reference=${repository}*:*`
   echo "成功进入源码文件夹"
	 docker build -t ${repository}:${version} .
	 echo "同步到阿里云仓库：版本号"
	 docker push ${repository}:${version}
	 echo "同步到阿里云仓库：latest"
	 docker tag `docker images -q --filter reference=${repository}:${version}` ${repository}:latest
	 docker push ${repository}:latest
	 echo "运行镜像"
	 docker-compose -f ${dockercompose} up -d nginx generalproject
	 echo "删除全部无效镜像"
   docker rmi -f $(docker images | grep "none" | awk '{print $3}')

else
   echo "源码文件夹进入失败"
	 exit
fi
