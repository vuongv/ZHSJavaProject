var imageNum = 1;
display(imageNum);
 

function nextSlide(t){
    display(imageNum += t);
}

function display(t) {
    var i;
    console.log(t);
    var image; 
    image = document.getElementsByClassName("currentImage");
    
    for (i = 0; i < image.length; i++) {
        image[imageNum-1].style.display = "block";
        image[i].style.display = "none";
        image[imageNum-1].style.display = "block";
        
    }
    
    imageNum++;
    if (imageNum > image.length) {
        imageNum = 1;
    }
    
    setTimeout(display, 4000);

}