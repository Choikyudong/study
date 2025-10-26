# Node.js 20 버전을 기반 이미지로 사용 (빌드 단계)
FROM node:20 AS builder
WORKDIR /app

# 패키지 파일 복사 및 의존성 설치
COPY package*.json ./
RUN npm install

# 소스 복사 및 빌드
COPY . .
RUN npm run build

# Nginx alpine 이미지를 기반으로 최종 이미지 생성
FROM nginx:alpine

# 빌드된 결과물을 Nginx 루트 복사 및 설정 파일 복사
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

# 컨테이너 시작시 사용할 명령어
CMD ["nginx", "-g", "daemon off;"]