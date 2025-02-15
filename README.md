# ToDoList

# Requirements

## Main Features

### Display Task List
- The app should show a list of tasks with a title and a completion status.

### Add New Task
- Provide a UI component (e.g., a button) that allows the user to navigate to a new screen or open a dialog where they can enter a task title.

### Mark Task as Completed
- Allow users to mark tasks as completed by clicking a checkbox or tapping the task.

### Delete Task
- Optionally, provide a way for users to delete tasks from the list.

## Data Persistence

- Use local storage (for example, Room) to save tasks so that they persist between app launches.

## Architecture

- Employ MVVM (Model-View-ViewModel) architecture or any modern architecture pattern you are comfortable with.
- Utilize LiveData, Flow, or another reactive approach to update the UI in response to data changes.

## UI/UX Considerations

- Ensure the app has a clean and responsive UI.
- Use Material Design components where appropriate.

## Extra Credit (if time permits)

- Add a feature to edit existing tasks.
- Implement sorting or filtering (e.g., show only incomplete tasks).
- Write unit tests or UI tests for your code.
