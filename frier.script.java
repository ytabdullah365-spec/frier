let designs = [];

function addDesign() {
    const title = document.getElementById("title").value.trim();
    const creator = document.getElementById("creator").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const file = document.getElementById("photo").files[0];

    if (!title || !creator || !phone || !file) {
        alert("Please fill all fields and choose an image.");
        return;
    }

    if (!file.type.startsWith("image/")) {
        alert("Please select an image.");
        return;
    }

    if (file.size > 2 * 1024 * 1024) {
        alert("Image must be smaller than 2 MB.");
        return;
    }

    const reader = new FileReader();

    reader.onload = function() {
        const digits = phone.replace(/\D/g, "");

        if (digits.length < 8 || digits.length > 15) {
            alert("Enter a valid WhatsApp number with country code.");
            return;
        }

        const design = {
            title: title,
            creator: creator,
            phone: digits,
            image: reader.result
        };

        designs.unshift(design);
        showDesigns();

        document.getElementById("title").value = "";
        document.getElementById("creator").value = "";
        document.getElementById("phone").value = "";
        document.getElementById("photo").value = "";

        alert("Design added successfully!");
    };

    reader.readAsDataURL(file);
}

function showDesigns() {
    const gallery = document.getElementById("gallery");
    gallery.innerHTML = "";

    designs.forEach(function(design) {
        const card = document.createElement("div");
        card.className = "card";

        const image = document.createElement("img");
        image.src = design.image;
        image.alt = design.title;

        const heading = document.createElement("h3");
        heading.textContent = design.title;

        const name = document.createElement("p");
        name.textContent = "Creator: " + design.creator;

        const contact = document.createElement("a");
        contact.href = "https://wa.me/" + design.phone;
        contact.target = "_blank";
        contact.rel = "noopener noreferrer";
        contact.textContent = "Contact on WhatsApp";

        card.append(image, heading, name, contact);
        gallery.appendChild(card);
    });
}

document.getElementById("search").addEventListener("input", function() {
    const query = this.value.toLowerCase();

    document.querySelectorAll(".card").forEach(function(card) {
        card.style.display = card.textContent.toLowerCase().includes(query)
            ? ""
            : "none";
    });
});