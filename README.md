# 메이플스토리 보스 매칭 서비스


## Git Commit 메시지 규칙

프로젝트의 일관성을 유지하고 협업을 원활하게 하기 위해, 다음과 같은 Git Commit 메시지 규칙을 따릅니다:

### 메시지 형식

- `type`: 커밋의 유형을 지정합니다.
    - `feat`: 새로운 기능 추가
    - `fix`: 버그 수정
    - `docs`: 문서 변경
    - `style`: 코드 스타일 변경 (형식, 공백 등), 기능에 영향을 미치지 않는 변경
    - `refactor`: 코드 리팩토링, 기능 변경 없이 코드 구조를 개선
    - `test`: 테스트 추가 또는 수정
    - `chore`: 빌드 프로세스 또는 보조 도구 변경, 라이브러리 업데이트 등

- `scope` (선택 사항): 변경된 부분의 범위나 기능을 나타냅니다.
- `subject`: 짧고 명확한 설명으로, 첫 글자는 소문자로, 문장의 끝에 마침표를 사용하지 않습니다.

### 예시

- `feat(parser): add ability to parse arrays`
- `fix(login): handle authentication error correctly`
- `docs(readme): update installation instructions`

모든 커밋 메시지는 명확하고 간결하게 작성하며, 코드 리뷰와 변경 추적을 용이하게 합니다.