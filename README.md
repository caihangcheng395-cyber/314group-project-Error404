# CSIT314 Interior Design Platform — Cross-platform Starter

**适用：macOS + Windows｜Java 17｜Spring Boot｜React + Vite｜H2｜BCE**

> **重要：这是团队公共基础框架，不是48个User Stories的最终实现。** 当前真正实现并可通过前后端访问的示例功能是 **View IDP Category（列表及详情）**。其他功能需要组员继续开发。此仓库由示例代码构成，不能代替真实的Scrum、TDD和CI/CD过程证据。

## 1. 你能看到什么？

打开网页后，页面从后端 `/api/categories` 读取 H2 数据库中的示例分类，并支持搜索及点击查看详情。浏览器访问的是前端，数据不是硬编码在React组件中的。

```
Browser → React (frontend) → HTTP /api/categories
                            ↓
                Boundary: ViewIDPCategoryPage
                            ↓
                Control: ViewIDPCategoryController
                            ↓
                Repository: IDPCategoryRepository
                            ↓
                Entity: IDPCategory → H2 Database
```

## 2. 推荐：Mac/Windows 使用 Docker Desktop（一条命令）

**要求：** 安装并启动 Docker Desktop，首次运行需要网络下载镜像和依赖。

在本项目根目录运行：

```bash
docker compose up --build
```

浏览器打开 **http://localhost:3000**。后端API可在 **http://localhost:8080/api/categories** 检查。

停止：`Ctrl+C`；关闭容器：`docker compose down`。默认数据库保存在Docker卷中，`docker compose down -v` **会删除该数据库卷**。

## 3. 不使用Docker：Mac/Windows 本地运行

**安装：** JDK 17、Maven 3.9+、Node.js 20（推荐20 LTS）和npm。首次安装依赖需要网络。不要将Maven的`target/`或Node的`node_modules/`提交到GitHub。

在第一个终端：

```bash
cd backend
mvn spring-boot:run
```

在第二个终端：

```bash
cd frontend
npm install
npm run dev
```

访问 **http://localhost:5173**。Vite开发代理会将`/api`请求转发到`http://localhost:8080`，因此无需在React里写死服务器地址。

Windows PowerShell和Mac Terminal均可使用以上命令。`cd`要从项目根目录开始执行；也可用VS Code分别打开两个终端。

**后端H2文件数据库**默认存放在`backend/data/`。第一次启动会自动创建一些示例分类；后续启动不会重复插入。仅用于课程开发演示，不适合真实生产数据。

## 4. 运行测试

```bash
cd backend
mvn test
```

```bash
cd frontend
npm install
npm test
npm run build
```

GitHub Actions在推送和Pull Request时执行对应测试/构建。首次 `npm install` 后建议把生成的 `package-lock.json` 提交到仓库，再将团队和CI的安装命令统一改为 `npm ci`，使依赖可复现。`delivery.yml`在推送版本tag时发布Docker镜像到GitHub Container Registry；**发布镜像不等于已经完成线上部署**，课程要求的部署过程及证据仍需真实完成。

## 5. 六人开发原则

- **只有一个仓库、一个后端、一个前端、一个共享数据模型**；不要各自复制一个项目。
- 每人从`main`创建分支：`feature/member1-auth`、`feature/member6-category`等。
- 新功能先在`docs/api-contract.md`登记接口，再实现Boundary → Controller → Entity/Repository → Frontend。
- 不得重复定义共享实体（例如`User`、`IDP`）；修改共享字段先与团队确认。
- 每个Story应有Task、测试、UML和可追踪的提交记录；提交PR后再合并。
- **真实**保留Sprint、会议纪要、TDD Red/Green/Refactor、CI/CD及Demo截图。

请依次阅读：

1. `docs/architecture.md` — BCE代码映射与数据流
2. `docs/team-plan.md` — 六人模块分工、共享实体归属
3. `docs/api-contract.md` — 接口约定
4. `docs/database-design.md` — 已实现和规划的数据结构
5. `docs/assignment-checklist.md` — 按作业PDF检查提交材料
6. `docs/workflow-and-evidence.md` — Git、TDD、Scrum、CI/CD证据
7. `docs/feature-task-template.md` — 每个User Story可复制的任务清单
8. `docs/troubleshooting.md` — Mac/Windows常见问题

## 6. 环境和限制

- 课程PDF要求至少Backend/Middleware采用面向对象设计，并使用BCE；**并没有指定必须Spring Boot、React或H2**。
- 当前示例没有用户认证、授权、收藏、报表、图片上传、生产级数据迁移或完整48个故事。
- 示例使用`ddl-auto: update`，适合初期开发；正式部署需要数据库迁移、权限控制、输入校验、备份及安全配置。
- API和实体类命名以本仓库为起点，实际提交前需与组内最终UML保持一致。
- 如果`3000`、`5173`或`8080`端口被占用，先关闭占用程序或修改对应端口配置。

## 7. 代码目录

```
frontend/                 React + Vite 页面和API调用
backend/src/main/java/    Spring Boot BCE + Repository
backend/src/test/java/    JUnit + Spring MVC + 集成测试
.github/workflows/        CI 与容器镜像交付流程
frontend/nginx.conf          Docker前端反向代理
compose.yaml              Docker本地一键运行
scripts/                  Mac/Windows环境检查脚本
docs/                     组员协作、设计、提交清单
```

> **验证状态：** 此ZIP已做本地静态文件和结构检查；当前生成环境无法访问外部依赖仓库，也没有Maven可执行文件，因此未在这里完成`mvn test`或`npm run build`。请在你们联网的Mac/Windows电脑按以上步骤运行并记录实际结果。
