let contentList = document.querySelector(".content-list");

// 저장된 컨텐츠 불러오기
let lastIdx = localStorage.length;

let localTodos = JSON.parse(localStorage.getItem("todos"));
console.log(localTodos);

if (localTodos) {
  localTodos.forEach((obj) => makeContent(obj));
}

let input = document.querySelector("input");
let btn = document.querySelector("button");
btn.onclick = () => {
  makeContent({ content: input.value, done: false });
  saveTodoList();
  input.value = "";
};

function makeContent(obj) {
  let div = document.createElement("div");
  div.classList.add("content");
  div.textContent = obj["content"];

  if (obj && obj.done) {
    div.classList.add("done");
  }

  div.onclick = (event) => {
    // delete
    if (event.target.tagName == "BUTTON") {
      event.target.parentElement.remove();
    } else {
      // 체크 해제
      if (div.classList.contains("done")) {
        div.classList.remove("done");
        // 체크
      } else {
        div.classList.add("done");
      }
    }
    // localStorage update
    saveTodoList();
  };

  let divBtn = document.createElement("button");
  divBtn.classList.add("delete");
  divBtn.textContent = "delete";
  div.append(divBtn);
  contentList.append(div);
}

function saveTodoList() {
  let todos = [];
  document.querySelectorAll(".content").forEach((content) => {
    todos.push({
      content: content.firstChild.textContent,
      done: content.classList.contains("done"),
    });
  });
  localStorage.setItem("todos", JSON.stringify(todos));
}
