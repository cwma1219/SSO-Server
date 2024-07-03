FROM node:18.20.2 AS build

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package*.json ./

RUN npm install
RUN npm install react-scripts@4.0.3 -g
RUN npm install -g sass && npm install --save-dev sass

COPY . ./
EXPOSE 3000
CMD ["npm", "start"]

## docker build -t cas_ui -f Dockerfile . --no-cache
## docker run --name cas_ui -p 80:80 cas_ui
### --------------------------------------------
#FROM node:18.20.2 AS build
#
#WORKDIR /app
#
#ENV PATH /app/node_modules/.bin:$PATH
#
#COPY package*.json ./
#
#RUN npm install
#RUN npm install react-scripts@4.0.3 -g
#RUN npm install -g sass && npm install --save-dev sass
#
#COPY . ./
#RUN npm run build
#
#FROM httpd:2.4
#COPY --from=build /app/build/ /usr/local/apache2/htdocs/
#EXPOSE 80
###--------------------------------------------
# 使用 Node.js 镜像作为构建阶段
#FROM node:18.20.2 AS build
#
## 设置工作目录
#WORKDIR /app
#
## 将依赖项文件复制到工作目录
#COPY package*.json ./
#
## 安装依赖项
#RUN npm install
#RUN npm install react-scripts@4.0.3 -g
#RUN npm install -g sass && npm install --save-dev sass
#
## 复制应用程序代码到工作目录
#COPY . ./
#
## 构建 React 应用程序
#RUN npm run build
#
#COPY .htaccess /app/public/
#COPY .htaccess /app/src/
#
## 使用 Apache 镜像作为运行阶段
#FROM httpd:2.4
#
## 复制构建好的静态文件到 Apache 文档根目录
#COPY --from=build /app/build/ /usr/local/apache2/htdocs/
#
## 安装 nano 编辑器（如果需要的话）
#RUN apt-get update && apt-get install -y nano
#RUN apt-get update && apt-get install sudo
#
## 启用 mod_rewrite 模块
#RUN sed -i '/LoadModule rewrite_module/s/^#//g' /usr/local/apache2/conf/httpd.conf
#
## 配置 .htaccess 文件
#RUN echo "Options -MultiViews\n\
#RewriteEngine On\n\
#RewriteBase /\n\
#RewriteCond %{REQUEST_FILENAME} !-f\n\
#RewriteRule ^ index.html [QSA,L]" > /usr/local/apache2/htdocs/.htaccess
#
## 允许 Apache 读取 .htaccess 文件
#RUN sed -i '/<Directory "\/usr\/local\/apache2\/htdocs">/a AllowOverride All' /usr/local/apache2/conf/httpd.conf
#
## 启动 Apache 服务器
#CMD ["httpd-foreground"]
###--------------------------------------------
#FROM node:18.20.2 AS build
#
#WORKDIR /build
#COPY package.json package.json
#COPY package-lock.json package-lock.json
#RUN npm ci
#COPY public/ public
#COPY src/ src
##COPY .htaccess .htaccess
#RUN npm run build
#
#FROM httpd:2.4
#WORKDIR /build
#RUN apt-get update && apt-get install -y nano
#RUN apt-get update && apt-get install -y apache2
#RUN a2enmod rewrite
#COPY --from=build /build/build/ /usr/local/apache2/htdocs/
##COPY --from=build /build/.htaccess /usr/local/apache2/htdocs/