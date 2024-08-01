$(document).ready(() => {
    $(".main__form-container").submit((e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const contactAccountDTO = {
            phoneNumber: formData.get("phoneNumber"),
            name: formData.get("name"),
            password: formData.get("password")
        };
        $.ajax({
            url: "/edit_contact_data/save",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(contactAccountDTO),
            success: (data, textStatus, jqXHR) => {
                const locationHeader = jqXHR.getResponseHeader("Location");

                if (locationHeader) {
                    window.location.href = locationHeader;
                } else {
                    console.error("Location header is missing in the response.");
                }
            },
        });
    });
});