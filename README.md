# paramount

### Spring Boot Project

该项目重构于2018年12月

### 准备工作
1. 运行doc中的两个sql文件
2. 依赖了fastdfs一个jar包,中央仓库没有,请自行安装到maven仓库中
3. 项目依赖redis,solr,fastdfs,请自行安装配置

### 开发阶段请运行 `ServerApplication`

### 部署指南:

#### <1>首次部署当前程序需要在对应的文件夹中执行以下命令

1. 启动程序 nohup java -jar paramount.jar &
2. 退出 ctrl + c
3. 查看日志 tail -500f nohup.out

#### <2>非首次部署当前程序需要在对应的文件夹中执行以下命令

1. 捕获上一个版本程序的进程 ps - ef|grep paramount.jar
2. 杀死对应的进程 kill 进程号
3. 启动程序 nohup java -jar paramount.jar &
4. 退出 ctrl + c
5. 查看日志 tail -500f nohup.out