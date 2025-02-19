# ModSync Server
A simple server for [ModSync](https://github.com/littlesquirt1/ModSync).

## Usage
1. Download the latest release from the releases page.
2. Place mods in the `client_mods` folder, in the same folder as the jar file.
3. Place the [latest version of ModSync](https://github.com/littlesquirt1/ModSync/releases/latest) in the `mod_sync_client` folder, once again, in the same folder as the jar file.
4. Run the server jar file.
    ```shell
    java -jar modsync-server-VERSION.jar
    ```
    Or for a custom port:
    ```shell
    java -jar modsync-server-VERSION.jar --server.port=<port>
    ```