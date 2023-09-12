// 캔버스 생성
const canvasSize = 250;
const snakeSize = 25;

const score = document.getElementById("score");
const canvas = document.getElementById("canvas");
const ctx = canvas.getContext("2d");
ctx.strokeRect(0, 0, canvasSize, canvasSize);

let x = 0;
let y = 0;

// 상, 우, 하, 좌
const directionX = [0, snakeSize, 0, -snakeSize];
const directionY = [snakeSize, 0, -snakeSize, 0];

const snakeListX = [];
const snakeListY = [];

let snakeLength = 0;

let count = 0;
let dir = 0;

let removeX;
let removeY;

let foodX = 0;
let foodY = 0;

// 키보드 이벤트
window.onkeydown = function (e) {
  switch (e.keyCode) {
    // up
    case 38:
      dir = 2;
      break;
    // down
    case 40:
      dir = 0;
      break;
    // left
    case 37:
      dir = 3;
      break;
    case 39:
      // right
      dir = 1;
      break;
  }
};

function getRandomInt(max) {
  return Math.floor(Math.random() * max);
}

let interval;

// 게임시작
let startBtn = document.querySelector("#start");
startBtn.onclick = () => {
  if (!interval) {
    initGame();
    console.log("start");
    startBtn.disabled = true;
    endBtn.disabled = false;
    interval = setInterval(run, 200);
  }
};

let endBtn = document.querySelector("#end");
endBtn.onclick = () => {
  endGame();
};

// 게임 종료
function endGame() {
  console.log("게임종료");
  clearInterval(interval);
  interval = null;
  startBtn.disabled = false;
  endBtn.disabled = true;
}

function initGame() {
  ctx.clearRect(0, 0, canvasSize, canvasSize);
  snakeLength = 0;
  score.textContent = snakeLength;
  x = 0;
  y = 0;
  snakeListX.length = 0;
  snakeListY.length = 0;
  count = 0;
  dir = 0;
  foodX = 0;
  foodY = 0;
}

function run() {
  // 이전 경로 지우는 로직
  if (snakeListX.length == snakeLength) {
    removeX = snakeListX[0];
    removeY = snakeListY[0];
    ctx.clearRect(removeX, removeY, snakeSize, snakeSize);
    snakeListX.shift();
    snakeListY.shift();
  }

  // 몸에 닳으면 게임 종료
  if (isDuplicatedPosion(snakeListX, snakeListY, x, y)) {
    endGame();
  }

  // 새로운 경로 생성
  snakeListX.push(x);
  snakeListY.push(y);
  ctx.fillStyle = "rgba(0, 200, 0, 0.5)";
  ctx.fillRect(x, y, snakeSize, snakeSize);

  // 다음 경로 지정
  x += directionX[dir];
  y += directionY[dir];

  // 다음 경로가 벽에 부딪히면 게임종료
  if (x < 0 || y < 0 || x >= canvasSize || y >= canvasSize) {
    endGame();
  }

  // 먹이 먹었을 때
  if (snakeListX.slice(-1) == foodX && snakeListY.slice(-1) == foodY) {
    console.log("eat");
    makefood();

    ctx.fillStyle = "rgb(100,0,0,0.5)";
    ctx.fillRect(foodX, foodY, snakeSize, snakeSize);
    snakeLength++;
    score.textContent = snakeLength;
  }
}

function getAllIndexes(arr, val) {
  let indexes = [];
  for (let i = 0; i < arr.length; i++) {
    if (arr[i] == val) indexes.push(i);
  }
  return indexes;
}

function isDuplicatedPosion(arrX, arrY, valX, valY) {
  let indexes = getAllIndexes(arrX, valX);
  if (!indexes) return false;
  for (let index of indexes) {
    if (arrY[index] == valY) return true;
  }
  return false;
}

function makefood() {
  foodX = getRandomInt(canvasSize / snakeSize) * snakeSize;
  foodY = getRandomInt(canvasSize / snakeSize) * snakeSize;
  if (isDuplicatedPosion(snakeListX, snakeListY, foodX, foodY)) {
    console.log("food 중복");
    console.log(foodX, foodY);
    makefood();
  } else return;
}
