
      const form = document.getElementById("form");
      const address = document.getElementById("address");
      const mobile = document.getElementById("mobile");
      const recipient = document.getElementById("recipient");

      //Show input error messages
      function showError(input, message) {
        const formControl = input.parentElement;
        formControl.className = "form-control error";
        const small = formControl.querySelector("small");
        small.innerText = message;
      }

      //show success colour
      function showSucces(input) {
        const formControl = input.parentElement;
        formControl.className = "form-control success";
      }

      //checkRequired fields
      function checkRequired(inputArr) {
        inputArr.forEach(function (input) {
          if (input.value.trim() === "") {
            showError(input, `${getFieldName(input)} is required`);
          } else {
            showSucces(input);
          }
        });
      }

      //check input Length
      function checkLength(input, min, max) {
        if (input.value.length < min) {
          showError(
            input,
            `${getFieldName(input)} must be at least ${min} characters`
          );
        } else if (input.value.length > max) {
          showError(
            input,
            `${getFieldName(input)} must be les than ${max} characters`
          );
        } else {
          showSucces(input);
        }
      }

      //get FieldName
      function getFieldName(input) {
        return input.id.charAt(0).toUpperCase() + input.id.slice(1);
      }

      //Event Listeners
      form.addEventListener("submit", function (e) {
        e.preventDefault();
        checkRequired([mobile, address, recipient]);
        checkLength(mobile, 9, 12);
      });
