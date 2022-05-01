# Workspace configuration: MapReduce word count

1. Install IntelliJ IDE 2022.1 in Windows 11 without Java because this was installed in WSL 2.

2. Create  a `New Project` with  `Name: CountWord`; for this example, and `Location: \\wsl$\LinuxDistro`.

3. Import Hadoop's JARs in IDE, select the `File` tab and choose `Project Structure`. In `Project Setting/Modules` do click on the second `Add` and search the location of hadoop's JARs in `\\wsl$\LinuxDistro`
    ```
    hadoop-3.2.3\share\hadoop\common\hadoop-common-3.2.3.jar
    hadoop-3.2.3\share\hadoop\mapreduce\hadoop-mapreduce-client-core-3.2.3.jar
    hadoop-3.2.3\share\hadoop\mapreduce\hadoop-mapreduce-client-common-3.2.3.jar
    hadoop-3.2.3\share\hadoop\mapreduce\hadoop-mapreduce-client-jobclient-3.2.3.jar
    ```

4. After to create the code `CountWord.java`, select `Build Project` tab, this create the `out/producction`

5. Open again `Project Structure`, select `Artifacts`, and do click in `Add/JAR/From module with dependencies`, in dialog window select Main Class `CountWord` and do click in `OK`. After in the tab `Build` select `Build Artifacs`

6. Copy the files `CountWord.jar` and `input_file.txt` (Any text for example a book) in `~/data_hadoop`

7. Execute the next commands for count words using Hadoop MapReduce
    ```
    hadoop dfsadmin -safemode leave
    hadoop fs -mkdir /input_dir
    hadoop fs -put  ~/data_hadoop/input_file.txt /input_dir
    hadoop fs -ls /input_dir/
    hadoop dfs -cat /input_dir/input_file.txt
    hadoop jar ~/data_hadoop/CountWord.jar Countword /input_dir /output_dir
    ```

8. Show output
    ```
    hadoop dfs -cat /output_dir/*
    ```

Solve problems

1. Error in command `hadoop fs -put ...`
    ```
    stop-all.sh
    rm -rf /home/hdoop/hadoop-3.2.3/data/datanode/*
    start-all.sh
    ```

2. Error in Buils Project. Add Firewall permissions in PowerShell.
    ```
    New-NetFirewallRule -DisplayName "WSL" -Direction Inbound  -InterfaceAlias "vEthernet (WSL)"  -Action Allow

    Get-NetFirewallProfile -Name Public | Get-NetFirewallRule | where DisplayName -ILike "IntelliJ IDEA*" | Disable-NetFirewallRule
    ``` 
