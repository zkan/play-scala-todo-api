# play-scala-todo-api

```sh
curl -v localhost:9000/todos
```

```sh
curl -v localhost:9000/todos/1
```

```sh
curl -v -d '{"description": "some new item"}' -H 'Content-Type: application/json' -X POST localhost:9000/todos
```