async function createNewUser(user) {
        await fetch("/api/admin",
        {method: 'POST',headers: {'Content-Type': 'application/json'}, body: JSON.stringify(user)})
}

async function addNewUserForm() {
    const newUserForm = document.getElementById("newUser");

    newUserForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const name = newUserForm.querySelector("#firstName").value.trim();
        const surname = newUserForm.querySelector("#lastName").value.trim();
        const age = newUserForm.querySelector("#age").value.trim();
        const email = newUserForm.querySelector("#email").value.trim();
        const password = newUserForm.querySelector("#password").value.trim();

        const rolesSelected = document.getElementById("roles");

        let roles = [];
        for (let option of rolesSelected.selectedOptions) {
            if(option.value === ROLE_USER.role) {
                roles.push(ROLE_USER);
            } else if (option.value === ROLE_ADMIN.role) {
                roles.push(ROLE_ADMIN);
            }
        }

        const newUserData = {
            username: name,
            surname: surname,
            age: age,
            email:email,
            password: password,
            roleList: roles
        };

        await createNewUser(newUserData);
        newUserForm.reset();

        document.querySelector('a#show-users-table').click();
        await fillTableOfAllUsers();
    });
}