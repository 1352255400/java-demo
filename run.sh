#!/bin/bash
# 工作负责名称
deploy_name="demo"
# 服务名
service_name="dev-java-$deploy_name"
# harbor 项目名（镜像名）
image_name="dev-java"
k8s_name_space="dev-java"

#查看镜像id
IID=$(docker images | grep "$service_name" | awk '{print $1}')
echo "查看镜像id： $IID"
if [ -n "$IID" ]
then
    echo "删除镜像： $service_name image,IID=$IID"
    #删除镜像
    #docker rmi $IID
    echo "delete $IID image"
    #构建
    docker build -t $service_name .
    echo "build $service_name image"
else
    echo "没有镜像： $service_name image,build docker"
    #构建
    docker build -t $service_name .
    echo "build $service_name image"
fi

echo "推送镜像"
harbor_url="镜像仓库地址"
docker tag $service_name $harbor_url/$image_name/$service_name:${BUILD_VERSION}
docker push $harbor_url/$image_name/$service_name:${BUILD_VERSION}

# 删除镜像
echo "删除镜像"
docker rmi $harbor_url/$image_name/$service_name:${BUILD_VERSION}
docker rmi $service_name

# 出发k8s
echo "调用k8s滚动更新"
kubectl set image deploy $deploy_name *=$harbor_url/$image_name/$service_name:${BUILD_VERSION} -n $k8s_name_space