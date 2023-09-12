// 숫자게임
// 숫자는 0~100 까지 랜덤숫자로 이용자가 10 턴 내에 맞춰야 한다.
// 입력한 숫자가 맞으면 종료 알림와 재시작 버튼
// 입력한 숫자가 틀리면 정답보다 높은지 낮은지 출력
// 이전에 입력한 값도 함께 출력

// 재시작 버튼 생성
let input = document.createElement("input");
input.className = "startSubmit";
input.value = "Start new game";
input.type = "submit";
input.hidden = true;
document.querySelector(".resultParas").append(input);

const startBtn = document.querySelector(".startSubmit");
startBtn.onclick = function () {
  this.setAttribute("hidden", true);
  initGame();
};

const guesses = document.querySelector(".guesses");
const lastResult = document.querySelector(".lastResult");
const lowOrHi = document.querySelector(".lowOrHi");
const guessesSubmit = document.querySelector(".guessSubmit");
const guessField = document.querySelector(".guessField");

let randomNum;
let guessCount;
let resetButton;

function initGame() {
  // 입력창 활성화
  guessField.disabled = false;
  guessesSubmit.disabled = false;

  // 출력값 초기화
  const resetParas = document.querySelectorAll(".resultParas p");
  for (const resetPara of resetParas) {
    resetPara.textContent = "";
  }
  lastResult.style.backgroundColor = "white";

  // 게임 세팅
  randomNum = Math.floor(Math.random() * 100) + 1;
  guessCount = 1;
  console.log(randomNum);
}

function endGame() {
  // 입력창 비활성화
  guessField.disabled = true;
  guessesSubmit.disabled = true;

  // 재시작 버튼 생성
  startBtn.hidden = false;
}

function checkGuess() {
  let userNum = Number(guessField.value);

  // 이전 입력값 출력
  if (guessCount === 1) {
    guesses.textContent = "Previous guesses : ";
  }
  guesses.textContent += userNum + " ";

  // 정답일 경우
  if (userNum === randomNum) {
    lastResult.textContent = "Congratulations! You got it right!";
    lastResult.style.backgroundColor = "green";
    lowOrHi.textContent = "";

    // 게임 종료
    endGame();

    // 턴 초과일 경우
  } else if (guessCount === 10) {
    lastResult.textContent = "!!!GAME OVER!!!";
    endGame();

    // 오답일 경우
  } else {
    lastResult.textContent = `Wrong!`;
    lastResult.style.backgroundColor = "red";

    // 입력값이 작을 경우
    if (userNum < randomNum) {
      lowOrHi.textContent = "Last guess was too low!";

      // 입력값이 클 경우
    } else {
      lowOrHi.textContent = "Last guess was too high!";
    }
  }

  guessCount++;
  // 입력창 초기화, 커서 활성화
  guessField.value = "";
  guessField.focus();
}

initGame();
guessesSubmit.onclick = checkGuess;