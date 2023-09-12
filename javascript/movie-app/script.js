const API_URL =
  "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=3fd2be6f0c70a2a598f084ddfb75487c&page=1";
const IMG_PATH = "https://image.tmdb.org/t/p/w1280";
const SEARCH_API =
  "https://api.themoviedb.org/3/search/movie?api_key=3fd2be6f0c70a2a598f084ddfb75487c&query=";

const main = document.getElementById("main");
const form = document.getElementById("form");
const search = document.getElementById("search");

// original_title, overview, poster_path, vote_average
async function get_movie() {
  let response = await fetch(API_URL);
  let commits = await response.json(); // 응답 본문을 읽고 JSON 형태로 파싱함
  showMovie(commits);
}

get_movie();

form.addEventListener("submit", (e) => {
  e.preventDefault();
  searchMovie(search.value);
});

async function searchMovie(value) {
  let searchPath = SEARCH_API + value;
  let response = await fetch(searchPath);
  let result = await response.json();
  main.innerHTML = "";
  showMovie(result);
}

function showMovie(commits) {
  for (let i = 0; i < commits.results.length; i++) {
    // movie(poster_path), poster_path
    //<img src="IMG_PATH" alt ="대체 텍스트">
    let divMovie = document.createElement("div");
    divMovie.classList.add("movie");
    let poster_path = IMG_PATH + commits.results[i].poster_path;
    divMovie.innerHTML = `<img src=${poster_path} alt ="대체 텍스트">`;

    // movie-info(original_title, vote_average)
    let divMovieInfo = document.createElement("div");
    divMovieInfo.classList.add("movie-info");

    // title
    divMovieInfo.innerHTML = ` <h3>${commits.results[i].original_title}</h3>`;

    // vote
    let vote_average = commits.results[i].vote_average;
    let color = "";
    if (vote_average < 7) {
      color = "red";
    }
    divMovieInfo.innerHTML += `<span class="${color}"> ${vote_average} </span>`;

    //overview
    let divOverview = document.createElement("div");
    divOverview.classList.add("overview");
    divOverview.innerHTML = `<h3> Overview </h3> 
   ${commits.results[i].overview}`;

    divMovie.appendChild(divMovieInfo);
    divMovie.appendChild(divOverview);
    main.appendChild(divMovie);
  }
}
