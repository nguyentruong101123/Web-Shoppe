let index =0;
let auto;
function move(n){
    showSlide(index += n);
}
function showSlide(){
    let img = document.getElementsByClassName('mySlides');
    if(index >= img.length){
        index = 0;
    }
    if(index < 0){
        index = img.length-1;
    }
    for(let i = 0; i<img.length;i++){
        img[i].style.display='none';
    }
    img[index].style.display='block';
    index++;
    auto = setTimeout(showSlide  ,5000);
}
showSlide();

