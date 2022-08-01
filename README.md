# EzyRoulette
 
Check out "Document.pdf" for more infomations.

# How to run?

1. Set up repository:
    - Clone this repo:
      ```
      git clone https://github.com/tutrananh/EzyRoulette.git && cd EzyRoulette
      ````
    - Clone submodules `ezyfox-server-csharp-client`:
      ```
      git submodule update --init --recursive
      ```
2. Run server:
    - Install mongodb ([Tutorial](https://docs.mongodb.com/manual/administration/install-community/))
    - Create `ezyrolette` database with `user=root` and `password=123456` using mongo shell:
      ```
        > use ezyrolette;
        > db.temp.insert({"key": "value"});
        > db.createUser({user: "root", pwd: "123456", roles:[{role: "readWrite", db: "ezyrolette"}]})
      ```
    - Import collections in db_collections folder to db
    - Import ```server``` folder into an IDE (Eclipse, Intellij, Netbean)
    - Run file [ApplicationStartup.java](https://github.com/tutrananh/EzyRoulette/blob/main/server/EzyRoulette-startup/src/main/java/org/youngmonkeys/ApplicationStartup.java)
3. Run Unity client:
  - Add ```client-unity``` folder to Unity Hub and open it.
  - Open LoginScene: `Assets/1 - Static Assets/1.1 - Scenes/LoginScene.unity`
  - Run LoginScene
  
# Documentation

1. [ezyfox-server](https://youngmonkeys.org/project/ezyfox-sever/)
2. [C# client](https://github.com/youngmonkeys/ezyfox-server-csharp-client)

