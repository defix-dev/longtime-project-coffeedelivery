$(document).ready(() => {
    $(".register-form").submit((e) => {
        e.preventDefault();
        console.log(this);
        console.log(e);
        const formData = new FormData(e.target);
        const accountDTO = {
            phoneNumber: formData.get("reg-phoneNumber"),
            password: formData.get("reg-password"),
            name: formData.get("reg-username"),
            confirmPassword: formData.get("reg-repeat-password")
        };

        $.ajax({
            url: "/authentication/register",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(accountDTO),
            success: () => {
                console.log("SUCCESS");
                window.location.href = "/home";
            },
            error: (xhr) => {
                try {
                    const parsedJson = JSON.parse(xhr.responseText);
                    let errors = "Пожалуйста проверьте корректность введенных данных:\n";
                    for (let i = 0; i < parsedJson.length; i++) {
                        for (const key in parsedJson[i]) {
                            errors += `${i + 1}. [${key}] ${parsedJson[i][key]}\n`;
                        }
                    }
                    alert(errors);
                } catch {
                    alert(xhr.responseText);
                }
            }
        });
    });
});

regPanel = document.querySelector(".main__auth-panel--register");
regHeader = document.getElementById("main-reg-header");
logPanel = document.querySelector(".main__auth-panel--login");
logHeader = document.getElementById("main-log-header");

function login() {
    regPanel.style.display = "none";
    regHeader.style.display = "none";
    logPanel.style.display = "grid";
    logHeader.style.display = "flex block";
}

function register() {
    regPanel.style.display = "grid";
    regHeader.style.display = "flex block";
    logPanel.style.display = "none";
    logHeader.style.display = "none";
}

document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const currentForm = urlParams.get("form");

    if (currentForm == "register") {
        register()
    } else {
        login()
    }
});

document.querySelector(".log-btn").addEventListener("click", login);
document.querySelector(".reg-btn").addEventListener("click", register);