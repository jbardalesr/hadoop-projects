# Workspace configuration: Install Hadoop on WSL 2 Ubuntu 22.04 LTS

1. Download image from https://cloud-images.ubuntu.com/jammy/current/ and select `jammy-server-cloudimg-amd64-wsl.rootfs.tar.gz`

2. Execute the next command in PowerShell `wsl --import UbuntuHadoop %LOCALAPPDATA%\Packages\UbuntuHadoop jammy-server-cloudimg-amd64-wsl.rootfs.tar.gz`


This is a combination of this tutorial https://phoenixnap.com/kb/install-hadoop-ubuntu and my classes in the university 
```
sudo apt update
sudo apt install openjdk-8-jdk -y
java -version | javac -version
```

Set Up a Non-Root User for Hadoop Environment 

Create Hadoop User

Configure Hadoop Environment Variables (bashrc)

`sudo nano ~/.bashrc`

```
# Environment variables
export HADOOP_HOME=/home/hdoop/hadoop-3.2.3
export HADOOP_INSTALL=$HADOOP_HOME
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
export HADOOP_OPTS="$HADOOP_OPTS -Djava.library.path=$HADOOP_HOME/lib/native"
```

Execute `source ~/.bashrc`

Edit hadoop-env.sh file

Edit core-site.xml file `sudo nano $HADOOP_HOME/etc/hadoop/core-site.xml`
```html
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value> <!-- It's important that it be localhost no 127.0.0.1-->
    </property>
</configuration>
```

Edit hdfs-site.xml file `sudo nano $HADOOP_HOME/etc/hadoop/hdfs-site.xml`
```html
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
    <property>
        <name>dfs.namenode.name.dir</name>
        <value>/home/hdoop/hadoop-3.2.3/data/namenode</value>
    </property>
    <property>
        <name>dfs.datanode.data.dir</name>
        <value>/home/hdoop/hadoop-3.2.3/data/datanode</value>
    </property>
</configuration>
```

Edit mapred-site.xml file `sudo nano $HADOOP_HOME/etc/hadoop/mapred-site.xml`
```html
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
</configuration>
```

Edit yarn-site.xml file `sudo nano $HADOOP_HOME/etc/hadoop/yarn-site.xml`
```html
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.env-whitelist</name>
        <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PERPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME
        </value>
    </property>
</configuration>
```
To be continued ...

Util comands
```
# In Ubuntu
service ssh restart # solve Connection refused
netstat -tlpn # check open ports in ubuntu
git config --global credential.helper cache

# In PowerShell
Test-NetConnection localhost -p 8088 # check connection to an specific port in Windows
netsh interface portproxy add v4tov4 listenport=8088 listenaddress=0.0.0.0 connectport=8088 connectaddress=$($(wsl hostname -I).Trim()); #https://docs.microsoft.com/en-us/windows-server/networking/technologies/netsh/netsh-interface-portproxy
netsh interface portproxy delete v4tov4 listenport=8088 listenaddress=192.168.1.15
wsl hostname -I # ip del wsl
```

To be continued ...

Compile with java
```
javac HelloWorld.java
java -cp . HelloWorld
```