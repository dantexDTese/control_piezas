package Controller.ProduccionController;

import Model.ProduccionModel.SeguimientoProduccionModel;
import View.Produccion.SeguimientoProduccionDialogView;

/**
 *
 * @author cesar
 */
public class SeguimientoProduccionController {

    
    private final SeguimientoProduccionDialogView vistaSeguimiento;
    private final SeguimientoProduccionModel seguimientoProduccionModel;
    
    SeguimientoProduccionController(SeguimientoProduccionDialogView vistaSeguimiento, SeguimientoProduccionModel seguimientoProduccionModel) {
        this.vistaSeguimiento = vistaSeguimiento;
        this.seguimientoProduccionModel = seguimientoProduccionModel;
    }
    
    
    
}
