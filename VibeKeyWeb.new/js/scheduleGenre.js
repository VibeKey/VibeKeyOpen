var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
var genresRef = fireRef.child("genres");
var curGenreRef = fireRef.child("curGenre");

//populate drop down in schedule genre
genresRef.on("value", function(snapshot) {
  var genresDropdown = document.getElementById("genresList");
  snapshot.forEach(function(data) {
    var genre = data.val();
    var option = document.createElement("option");
    option.text = option.value = genre;
    // genresDropdown.appendChild(option);
    $('.genresList').append(option);
  });
});

function genreClicked(){
  var genresDropdown = document.getElementById("genresList");
  var selectedGenre = genresDropdown.value;
  console.log(selectedGenre);
  //curGenreRef.set("none");
}

function clearGenre(){
  curGenreRef.set("none");
}