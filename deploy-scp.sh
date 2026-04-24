#!/bin/bash
# 最简单的部署脚本 - 只使用 scp
# 用法: ./deploy-scp.sh
# 然后提示输入密码，输入密码即可

# 服务器配置
SERVER="101.132.78.93"
TARGET="/var/java/my-java-app"

echo "1. 构建项目..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ 构建失败"
    exit 1
fi

echo "✅ 构建成功"

echo "2. 上传到服务器..."
echo "目标: $SERVER:$TARGET"

# 直接使用 scp（手动输入密码）
scp -r target/* root@"$SERVER":"$TARGET"/

if [ $? -eq 0 ]; then
    echo "✅ 上传成功！"
    echo "🌐 访问地址: http://$SERVER"
else
    echo "❌ 上传失败"
    exit 1
fi