const fs = require('fs');
const path = require('path');

// Heroicons 경로
const iconsSourcePath = path.resolve(__dirname, '../node_modules/@heroicons/react/24/solid');

// 프로젝트 정적 리소스 경로
const iconsDestinationPath = path.resolve(__dirname, '../src/main/resources/static/icons');

// 복사할 아이콘 목록
const icons = ['moon.svg', 'sun.svg'];

// 아이콘 디렉토리가 없으면 생성
if (!fs.existsSync(iconsDestinationPath)) {
    fs.mkdirSync(iconsDestinationPath, { recursive: true });
}

// 아이콘 복사
icons.forEach(icon => {
    const srcPath = path.join(iconsSourcePath, icon);
    const destPath = path.join(iconsDestinationPath, icon);
    if (fs.existsSync(srcPath)) {
        fs.copyFileSync(srcPath, destPath);
        console.log(`Copied ${icon} to ${iconsDestinationPath}`);
    } else {
        console.error(`Icon ${icon} not found in ${iconsSourcePath}`);
    }
});
