const promptInput = document.getElementById("promptInput");
const sendButton = document.getElementById("sendButton");
const chatBox = document.getElementById("chatBox");

const historyPanel = document.getElementById("historyPanel");
const historyContent = document.getElementById("historyContent");

let isSending = false;

async function sendPrompt() {
    const prompt = promptInput.value.trim();

    if (!prompt || isSending) {
        return;
    }

    isSending = true;

    addMessage("You", prompt, "user-message");

    promptInput.value = "";
    sendButton.disabled = true;
    sendButton.textContent = "Thinking...";

    const loadingMessage = addLoadingMessage();

    try {
        const response = await fetch("/api/ai/generate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                prompt: prompt
            })
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const data = await response.json();

        removeLoadingMessage(loadingMessage);

        addMessage(
            "ResolveFlow AI",
            data.reply || "No response received.",
            "ai-message"
        );

    } catch (error) {
        console.error("AI request failed:", error);

        removeLoadingMessage(loadingMessage);

        addMessage(
            "ResolveFlow AI",
            `Unable to generate a response. Error: ${error.message}`,
            "ai-message"
        );

    } finally {
        isSending = false;
        sendButton.disabled = false;
        sendButton.textContent = "Send";
        promptInput.focus();
    }
}

function addMessage(label, text, className) {
    const message = document.createElement("div");
    message.className = `message ${className}`;

    const messageLabel = document.createElement("div");
    messageLabel.className = "message-label";
    messageLabel.textContent = label;

    const messageContent = document.createElement("div");
    messageContent.className = "message-content";
    messageContent.textContent = text;

    message.appendChild(messageLabel);
    message.appendChild(messageContent);

    chatBox.appendChild(message);

    chatBox.scrollTop = chatBox.scrollHeight;

    return message;
}

function addLoadingMessage() {
    const message = document.createElement("div");
    message.className = "message ai-message loading-message";

    const label = document.createElement("div");
    label.className = "message-label";
    label.textContent = "ResolveFlow AI";

    const content = document.createElement("div");
    content.className = "message-content";
    content.textContent = "Thinking...";

    message.appendChild(label);
    message.appendChild(content);

    chatBox.appendChild(message);

    chatBox.scrollTop = chatBox.scrollHeight;

    return message;
}

function removeLoadingMessage(message) {
    if (message && message.parentNode) {
        message.parentNode.removeChild(message);
    }
}

async function loadHistory() {
    historyPanel.classList.add("open");
    historyContent.innerHTML = "Loading...";

    try {
        const response = await fetch("/api/ai/history");

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const history = await response.json();

        if (!Array.isArray(history) || history.length === 0) {
            historyContent.innerHTML =
                '<div class="empty-history">No conversation history yet.</div>';
            return;
        }

        historyContent.innerHTML = "";

        const newestFirst = [...history].reverse();

        newestFirst.forEach(item => {
            const historyItem = document.createElement("div");
            historyItem.className = "history-item";

            const promptTitle = document.createElement("strong");
            promptTitle.textContent = "You";

            const promptText = document.createElement("div");
            promptText.className = "history-prompt";
            promptText.textContent = item.prompt || "";

            const replyTitle = document.createElement("strong");
            replyTitle.textContent = "ResolveFlow AI";

            const replyText = document.createElement("div");
            replyText.className = "history-reply";
            replyText.textContent = item.reply || "";

            historyItem.appendChild(promptTitle);
            historyItem.appendChild(promptText);
            historyItem.appendChild(replyTitle);
            historyItem.appendChild(replyText);

            if (item.createdAt) {
                const date = document.createElement("div");
                date.className = "history-date";

                const parsedDate = new Date(item.createdAt);

                if (!Number.isNaN(parsedDate.getTime())) {
                    date.textContent = parsedDate.toLocaleString();
                    historyItem.appendChild(date);
                }
            }

            historyContent.appendChild(historyItem);
        });

    } catch (error) {
        console.error("History load failed:", error);

        historyContent.innerHTML =
            `<div class="empty-history">Unable to load history. ${error.message}</div>`;
    }
}

async function clearHistory() {
    const confirmed = confirm(
        "Are you sure you want to clear all conversation history?"
    );

    if (!confirmed) {
        return;
    }

    try {
        const response = await fetch("/api/ai/history", {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        historyContent.innerHTML =
            '<div class="empty-history">Conversation history cleared.</div>';

    } catch (error) {
        console.error("Clear history failed:", error);

        alert(`Unable to clear conversation history. ${error.message}`);
    }
}

function closeHistory() {
    historyPanel.classList.remove("open");
}

promptInput.addEventListener("keydown", function (event) {
    if (event.key === "Enter" && !event.shiftKey) {
        event.preventDefault();
        sendPrompt();
    }
});

document.addEventListener("keydown", function (event) {
    if (event.key === "Escape") {
        closeHistory();
    }
});

window.addEventListener("load", function () {
    promptInput.focus();
});