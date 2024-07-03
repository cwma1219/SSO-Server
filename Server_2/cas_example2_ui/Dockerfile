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