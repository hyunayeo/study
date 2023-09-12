let dir = 0;
let turn = 0;
let interval;

const lines = document.querySelector("#line");
const pieces = document.querySelector("#piece");
const button = document.querySelector(".play-button");

window.addEventListener(
  "keydown",
  (event) => {
    if (event.defaultPrevented) {
      return; // Do nothing if the event was already processed
    }

    switch (event.key) {
      case "ArrowDown":
        dir = 0;
        y += 1;
        // Do something for "down arrow" key press.
        break;
      case "ArrowUp":
        turn += 1;
        // Do something for "up arrow" key press.
        break;
      case "ArrowLeft":
        // Do something for "left arrow" key press.
        // dir = 1;
        x += -1;
        if (x < 0) x = 0;
        break;
      case "ArrowRight":
        // Do something for "right arrow" key press.
        // dir = 2;
        x += 1;
        if (x > COLS) x = COLS;
        break;
      case "Enter":
        // Do something for "enter" or "return" key press.
        break;
      case "Escape":
        // Do something for "esc" key press.
        break;
      default:
        return; // Quit when this doesn't handle the key event.
    }

    // Cancel the default action to avoid it being handled twice
    event.preventDefault();
  },
  true
);
const COLS = 10;
const ROWS = 20;
const BLOCK_SIZE = 20;

let ARRS;

const BLOCK_LIST = [];
const TETROMINOS = {
  I: {
    color: "skyblue",
    shape: [
      [
        [0, 0],
        [-1, 0],
        [1, 0],
        [2, 0],
      ],
      [
        [0, 0],
        [0, -1],
        [0, 1],
        [0, 2],
      ],
    ],
  },
  O: {
    color: "yellow",
    shape: [
      [
        [0, 0],
        [0, 1],
        [1, 0],
        [1, 1],
      ],
    ],
  },
  T: {
    color: "pink",
    shape: [
      [
        [0, 0],
        [1, 0],
        [0, -1],
        [-1, 0],
      ],
      [
        [0, 0],
        [0, -1],
        [0, 1],
        [1, 0],
      ],
      [
        [0, 0],
        [0, 1],
        [1, 0],
        [-1, 0],
      ],
      [
        [0, 0],
        [0, -1],
        [0, 1],
        [-1, 0],
      ],
    ],
  },
  J: {
    color: "blue",
    shape: [
      [
        [-1, 0],
        [-1, -1],
        [0, -1],
        [1, -1],
      ],
      [
        [0, 1],
        [-1, 1],
        [-1, 0],
        [-1, -1],
      ],
      [
        [1, 0],
        [1, 1],
        [0, 1],
        [-1, 1],
      ],
      [
        [0, -1],
        [1, -1],
        [1, 0],
        [1, 1],
      ],
    ],
  },
  L: {
    color: "orange",
    shape: [
      [
        [1, 0],
        [1, -1],
        [0, -1],
        [-1, -1],
      ],
      [
        [0, 1],
        [1, 1],
        [1, 0],
        [1, -1],
      ],
      [
        [-1, 0],
        [-1, 1],
        [0, 1],
        [1, 1],
      ],
      [
        [0, -1],
        [-1, -1],
        [-1, 0],
        [-1, 1],
      ],
    ],
  },
  S: {
    color: "green",
    shape: [
      [
        [0, 0],
        [1, 0],
        [0, 1],
        [-1, 1],
      ],
      [
        [0, 0],
        [0, 1],
        [-1, 0],
        [-1, -1],
      ],
    ],
  },
  Z: {
    color: "red",
    shape: [
      [
        [0, 0],
        [-1, 0],
        [0, 1],
        [1, 1],
      ],
      [
        [0, 0],
        [0, 1],
        [1, 0],
        [1, -1],
      ],
    ],
  },
};

const canvas = document.getElementById("canvas");
const ctx = canvas.getContext("2d");

ctx.canvas.width = COLS * BLOCK_SIZE;
ctx.canvas.height = ROWS * BLOCK_SIZE;
ctx.scale(BLOCK_SIZE, BLOCK_SIZE);

let x;
let y;

let shape;
let shapeIdx;
let positions;
let prePositions;
let tetromino;

const keys = Object.keys(TETROMINOS);

let isStop = true;
let lineCount = 0;
let pieceCount = 0;

function play() {
  initGame();
  interval = setInterval(run, 300);
}

function run() {
  if (isStop) {
    // 테이블에 정보 저장
    if (prePositions.length != 0) {
      pieceCount++;
      updateArr(prePositions);
      checkLine();
      updateScore();
    }

    let shapeIdx = Math.floor(Math.random() * keys.length);
    isStop = false;

    tetromino = TETROMINOS[keys[shapeIdx]];
    ctx.fillStyle = tetromino.color;

    turn = 0;
    x = 5;
    y = 0;
    positions = [];
  }

  prePositions = positions;

  if (turn >= tetromino.shape.length) {
    turn = 0;
  }
  shape = tetromino.shape[turn];
  positions = [];
  for (let cell of shape) {
    let nextX = cell[0] + x;
    let nextY = cell[1] + y;

    if (nextY >= ROWS) {
      isStop = true;
      positions = [];
      break;
    } else if (
      (nextX < 0) |
      (nextY < 0) |
      (nextX > COLS - 1) |
      (nextY > ROWS - 1)
    ) {
      continue;
    }

    // out of range
    if (ARRS[nextY][nextX] != 0) {
      isStop = true;
      positions = [];
      break;
    }
    positions.push([nextX, nextY]);
  }

  // 이전 그래픽 삭제
  if (positions.length != 0) {
    removeRac(prePositions);
    drawRac(positions);
  }

  y++;

  if (isStop && y == 1) {
    console.log("종료");
    endGame();
  }
}

function initGame() {
  button.disabled = true;
  updateScore();

  ctx.clearRect(0, 0, COLS, ROWS);
  ARRS = createArr();

  x = 5;
  y = 0;

  shapeIdx = 0;
  positions = [];
  prePositions = [];

  isStop = true;
  lineCount = 0;
  pieceCount = 0;
}

function endGame() {
  button.disabled = false;
  clearInterval(interval);
}

function removeRac(positions) {
  positions.forEach((item) => {
    ctx.clearRect(item[0], item[1], 1, 1);
  });
}

function drawRac(positions) {
  positions.forEach((position) => {
    ctx.fillRect(position[0], position[1], 1, 1);
  });
}

function updateArr(positions) {
  positions.forEach((position) => {
    ARRS[position[1]][position[0]] = 1;
  });
}

function checkLine() {
  // delete row
  for (let row = 0; row < ROWS; row++) {
    let line = ARRS[row];
    if (line.every((element) => element == 1)) {
      ARRS.splice(row, 1);
      ARRS.unshift(Array(COLS).fill(0));

      ctx.clearRect(0, 0, COLS, ROWS);

      lineCount++;
    }
  }

  // redraw canvas
  ctx.fillStyle = "gray";

  for (let i = 0; i < ROWS; i++) {
    for (let j = 0; j < COLS; j++) {
      if (ARRS[i][j] == 1) {
        ctx.fillRect(j, i, 1, 1);
      }
    }
  }
}

function updateScore() {
  lines.textContent = lineCount;
  pieces.textContent = pieceCount;
}

function createArr() {
  let table = [];
  for (let i = 0; i < ROWS; i++) {
    table.push(Array(COLS).fill(0));
  }
  return table;
}
