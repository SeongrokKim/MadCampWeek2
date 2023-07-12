# 음악인 다 됐네
[앱로고]

---
`MadCamp Week2`
- 음악 연습을 기록하여 저장하는 어플리케이션입니다.
- 게시판을 통해 다른 유저와 소통할 수 있습니다.
- 달력에서 날짜를 선택해 해당 날짜에 연습을 얼마나 했는지 확인할 수 있습니다.
- 비밀번호, 프로필 사진 등 개인 정보를 변경할 수 있습니다.
---
## 팀원
- [김성록](https://github.com/SeongrokKim)
- [김혜연](https://github.com/fairykhy)
---
## 개발 환경
- OS: Android (minSdk: 24, targetSdk: 33)
- Language: Java
- IDE: Android Studio
- Target Device: Galaxy S10e
---
## 프로젝트 설명
### 초기 화면
- **앱 실행**
<img src="https://github.com/SeongrokKim/MadCampWeek2/assets/138121077/b5a4b63b-6763-45ad-8233-85ebc966c179" width="250" height="500"/>
<img src="https://github.com/SeongrokKim/MadCampWeek2/assets/138121077/f25d4990-fed6-4444-a92e-ceec812fcdcd" width="250" height="500"/>

- **핵심 기능**
  - 회원가입을 할 수 있습니다.
  - 카카오, 구글을 통해 소셜 로그인이 가능합니다.
  - 회원가입한 정보로 로그인할 수 있습니다.

- **기술 설명**
  - db에 저장된 아이디와 비밀번호가 일치하였을 때 서버에서 성공 요청을 보내서 로그인이 됩니다.
  - 회원 가입 시 db에 저장된 아이디를 통해 중복 확인을 하고, 비밀번호 확인되었을 때 db에 유저 정보가 저장됩니다.
  - 카카오 sdk와 구글 sdk를 사용하여 소셜 계정으로 로그인이 가능합니다.
  - 자체 로그인 기능 시 배정되는 랜덤 UID와 카카오 ID, 구글 e-mail을 통해 유저를 구분합니다.

### 메인 화면
- **앱 실행**
<img src="https://github.com/SeongrokKim/MadCampWeek2/assets/138121077/4015da01-f564-4a4e-9938-fa177713ff7e" width="250" height="500"/>

- **핵심 기능**
  - 바텀 네비게이션을 통해 탭을 이동할 수 있습니다.
  - 왼쪽 끝에서 오른쪽으로 스와이프하거나 왼쪽 상단의 메뉴 버튼을 통해 패널을 열 수 있습니다.
  - 패널에서 메트로놈 기능과 연습 기록 기능을 선택할 수 있고, 로그아웃이 가능합니다.
  - 오른쪽 상단 왕관 버튼을 통해 연습 시간으로 정렬된 순서를 확인할 수 있습니다.

- **기술 설명**
  - Main Activity에서 세 fragment를 연결하고, bottom navigation view로 구현하여 이동이 가능합니다.
  - DrawerLayout을 사용하여 패널을 구현했습니다.
  - 로그아웃을 시도하면 로그인한 방식에 따라 다르게 로그아웃을 시도하여 초기 화면으로 이동합니다.
  - db에서 연습 총 시간을 내림차순으로 정렬하여 상위 5명의 명단이 다이얼로그를 통해 표시됩니다.

### 메트로놈, 연습 기록 화면
- **앱 실행**
<img src="https://github.com/SeongrokKim/MadCampWeek2/assets/138121077/6da086ec-6842-491b-884c-22111d8fc098" width="250" height="500"/>

- **핵심 기능**
  - 메트로놈 기능을 통해 박자를 맞춰 연습할 수 있습니다.
  - 사용자가 원하는 박자를 직접 지정할 수 있습니다.
  - 연습한 시간을 기록하는 시간 측정 기능과, 상세 정보를 입력하여 저장할 수 있습니다.
  - 해당 연습 정보는 db에 저장됩니다.

- **기술 설명**
  - 정확한 박자가 필요한 메트로놈을 구현하기 위해 짧은 소리 재생에 효율적인 Soundpool을 이용하였습니다.
  - bpm을 이용하여 소리가 재생되는 간격을 계산하고, 이를 이용하여 Soundpool을 실행합니다.
  - 안드로이드 스튜디오 내장 animation 기능을 이용하여 메트로놈 실행시 박자에 맞는 애니메이션을 실행합니다.
  - 연습한 항목(악기, 노래), 연습한 곡명, 연습 횟수, 연습 시간, 연습 소감을 기록할 수 있습니다.
  - 필수 항목은 연습 항목이며 기록시 UID마다 연습기록을 db에 저장합니다.
  - 기록한 연습기록은 홈 화면의 달력에서 바로 확인할 수 있습니다.

### 게시판 탭
- **앱 실행**
<img src="https://github.com/SeongrokKim/MadCampWeek2/assets/138121077/fbf8a78d-2cfd-4078-8b5e-4e2ec2800aee" width="250" height="500"/>

- **핵심 기능**
  - 게시글을 작성할 수 있습니다.
  - 다른 사람이 게시한 글을 볼 수 있고, 댓글을 달 수 있습니다.
  - 본인이 작성한 게시글을 수정할 수 있습니다.
  - 게시글 혹은 이름에 포함된 글자를 통해 검색할 수 있습니다.

- **기술 설명**
  - Recycler View를 통해 저장되어 있는 게시글 정보를 보여줍니다.
  - 게시글은 데이터베이스에 저장되어 다른 사람들이 접근할 수 있습니다.
  - 유저별 UID를 통해 본인이 작성한 글에만 편집 기능을 사용할 수 있습니다.

### 홈 탭
- **앱 실행**
<img src="https://github.com/SeongrokKim/MadCampWeek2/assets/138121077/17a3d0f9-bc43-4a96-a6a7-6ed6219db47a" width="250" height="500"/>

- **핵심 기능**
  - 달력을 통해 날짜를 확인할 수 있습니다.
  - 오늘은 다른 색깔로 나타내어 쉽게 확인이 가능합니다.
  - 양력 공휴일은 빨간색으로 지정됩니다.
  - 특정 날짜를 선택하면 해당 날짜에 연습한 기록을 확인할 수 있습니다.
  - 버튼을 통해 이전 달, 다음 달로 이동 가능합니다.
  - 연습 기록 클릭 시 상세 정보를 확인할 수 있습니다.

- **기술 설명**
  - Recycler View의 Grid Layout을 통해 달력을 구현하였습니다.
  - 달력에 표시되어 있는 달에 연습한 기록을 db에서 불러옵니다.
  - 각 날짜는 아이템으로 들어가있으며 터치 시 해당 날짜에 해당하는 연습 기록을 db에서 불러온 정보에서 찾아 보여줍니다.
  - 연습 기록은 Recycler View에 Card View로 보여주며, 카드 뷰 클릭시 intent로 정보를 넘겨서 상세 정보를 보여줍니다.


### 설정 탭
- **앱 실행**
<img src="https://github.com/SeongrokKim/MadCampWeek2/assets/138121077/15a1d1db-1cf5-4475-9517-5a96f4b55812" width="250" height="500"/>

- **핵심 기능**
  - 본인의 프로필 사진, 이름, 한 줄 소개를 확인할 수 있습니다.
  - 지금까지 연습한 총 시간과 본인의 순위를 확인할 수 있습니다.
  - 프로필 사진, 비밀번호, 한 줄 소개를 변경할 수 있습니다.
  - 현재 앱 버전을 확인할 수 있습니다.

- **기술 설명**
  - db에서 특정 UID 검색을 통해 개인별 총 연습 시간을 계산합니다.
  - 갤러리 접근 요청을 보내고 사진을 선택하게 되면 프로필 사진이 변경됩니다.
  - UID를 서버에 보내서 db에 비밀번호 정보를 비교하며 비밀번호를 변경할 수 있습니다.
  - 현재 비밀번호를 알아야 가능하며, 현재 비밀번호와 같은 번호로 변경은 불가능합니다.
  - 한 줄 소개도 비밀번호와 마찬가지로 UID를 통해 개인의 한 줄 소개 정보를 가져와서 변경합니다.
