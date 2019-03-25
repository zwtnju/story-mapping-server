# MapStoryClient

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 7.0.6.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).

## api文档
##### 注册

request

```shell
POST /api/signup
```

```json
{
    "userEmail": "test1@nju.com",
    "userPassword": "qwe123",
    "userName": "测试用户"
}
```

response

```json
{
    "status": 0, // 0-成功 1-权限不够 2-格式非法
    "data": {
    	"userToken": "ae123asqere21asdsa3",
    	"userId": 3,
    	"userEmail": "test1@nju.com",
    	"userName": "测试用户1"
	}
}
```



##### 登陆

request

```shell
POST /api/signin
```

```json
{
    "userEmail": "test1@nju.com",
    "userPassword": "qwe123"
}
```

response

```json
{
    "status": 0, // 0-成功 1-权限不够 2-格式非法
    "data": {
    	"userToken": "ae123asqere21asdsa3",
    	"userId": 3,
    	"userEmail": "test1@nju.com",
    	"userName": "测试用户1"
	}
}
```



##### 项目列表

request

```shell
POST /api/project/list
```

```json
{
    "userToken": "ae123asqere21asdsa3"
}
```

response

```json
{
    "status": 0, // 0-成功 1-权限不够 2-格式非法
    "data": [
        {
          "projectId": 3,
          "projectTitle": "项目题目",
          "projectContent": "项目说明",
          "ownerId": 3,
          "ownerEmail": "test1@nju.com",
          "ownerName": "测试用户1"
        },
        {
          
        }
    ]
}
```



##### 获取项目数据

request

```shell
POST /api/project/get
```

```json
{
    "userToken": "ae123asqere21asdsa3",
    "projectId": 3
}
```

response

```json
{
    "status": 0, // 0-成功 1-权限不够 2-格式非法
    "data": {
        "ownerId": 3,
        "ownerEmail": "test1@nju.com",
        "ownerName": "测试用户1",
        "contributors": [
            {
				        "contributorId": 4,
    			      "contributorEmail": "test2@nju.com",
    			      "contributorName": "测试用户2"
        	  },
			      {
          
        	  }
        ],
    	  "projectTitle": "项目题目",
    	  "projectContent": "项目说明",
    	  "projectCards": [ 
            [ { "content": "card1" }, { "content": "card2" }, { "content": "card3" }, { "content": "card4" } ], 
            [ { "content": "card1" }, { "content": "card2" }, { "content": "card3" }, { "content": "card4" } ] 
		    ]
	}
}
```



##### 更新项目数据

request

```shell
POST /api/project/update
```

```json
{
    "userToken": "ae123asqere21asdsa3",
    "projectId": 3,
    "projectTitle": "项目题目",
    "projectCards": "项目说明",
    "cards": [ 
        [ { "content": "card1" }, { "content": "card2" }, { "content": "card3" }, { "content": "card4" } ], 
		    [ { "content": "card1" }, { "content": "card2" }, { "content": "card3" }, { "content": "card4" } ] 
	  ]
}
```

response

```json
{
    "status": 0, // 0-成功 1-权限不够 2-格式非法
    "data": null
}
```

