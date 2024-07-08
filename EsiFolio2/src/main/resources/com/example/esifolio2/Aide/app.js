const aides= document.querySelectorAll(".aide");

aides.forEach((aide) => {
	aide.addEventListener("click", () => {
		aide.classList.toggle("active");
	});
});

