package app.controllers.impls;

import app.anotations.Controller;
import app.anotations.NotFound;
import app.controllers.CrudController;
import app.model.impls.NewModel;

@Controller(baseUrl = "/newModel", type = NewModel.class, arrayType = NewModel[].class)
@NotFound(message = "New model you are looking for is not found")
public class NewModelController extends CrudController {}
