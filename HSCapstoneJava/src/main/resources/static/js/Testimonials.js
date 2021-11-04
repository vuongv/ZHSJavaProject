var testimonialNum = 1;
display(testimonialNum);
 

function nextSlide(t){
    display(testimonialNum += t);
}

function display(t) {
    var i;
    console.log(t);
    var testimonial; 
    testimonial = document.getElementsByClassName("currentTestimonial");
    
    for (i = 0; i < testimonial.length; i++) {
        testimonial[testimonialNum-1].style.display = "block";
        testimonial[i].style.display = "none";
        testimonial[testimonialNum-1].style.display = "block";
        
    }
    
    testimonialNum++;
    if (testimonialNum > testimonial.length) {
        testimonialNum = 1;
    }
    
    setTimeout(display, 4000);

}