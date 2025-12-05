
### 1. 更新软件包列表
```bash
sudo apt update
```
### 2. 安装 Tiger VNC Server
Debian 系统中安装 Tiger VNC 一般安装以下两个包：
```bash
sudo apt install tigervnc-standalone-server tigervnc-common -y
```

### 3. 启动 VNC Server
启动 VNC 服务器并设置分辨率和颜色深度，例如：
VNC Server 本质上是启动了一个带有 VNC 扩展的 X 服务器（Xvnc），并将它绑定到你指定的 display 编号上。

```bash
vncserver :1 -geometry 1920x1080 -depth 24
```
```
vncserver :1 -geometry 2160x1350 -depth 24
```

### 4.安装java
```
mkdir /opt/package/java -p &&cd /opt/package/java 
wget https://github.com/litongjava/oracle-jdk/releases/download/8u411/jdk-8u411-linux-x64.tar.gz
wget https://gitcode.com/ppnt/oracle-jdk/releases/download/8u411/jdk-8u411-linux-x64.tar.gz
mkdir /usr/java/ -p
tar -xf jdk-8u411-linux-x64.tar.gz -C /usr/java
export JAVA_HOME=/usr/java/jdk1.8.0_411
export PATH=$JAVA_HOME/bin:$PATH
java -version
```

### 5.启动tio-websockify
```
cd /data/apps/tio-websockify
java -jar tio-websockify-1.0.0.jar --vnc-server=localhost:5901
```

```
http://192.168.0.247/vnc.html
```