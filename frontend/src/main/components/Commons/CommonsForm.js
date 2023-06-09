import { Button, Form } from "react-bootstrap";
import { useForm } from "react-hook-form";

function CommonsForm({ initialCommons, submitAction, buttonLabel = "Create" }) {
    // Stryker disable all
    const {
        register,
        formState: { errors },
        handleSubmit,
    } = useForm({ defaultValues: initialCommons || {} });
    // Stryker enable all

    const testid = "CommonsForm";

    const curr = new Date();
    const today = curr.toISOString().substr(0, 10);
    const onemonthfromtoday = new Date(curr.getFullYear(), curr.getMonth()+1, curr.getDate()).toISOString().substr(0, 10);

    return (
        <Form onSubmit={handleSubmit(submitAction)}>
            {initialCommons && (
                <Form.Group className="mb-3">
                    <Form.Label htmlFor="id">Id</Form.Label>
                    <Form.Control
                        data-testid={`${testid}-id`}
                        id="id"
                        type="text"
                        {...register("id")}
                        value={initialCommons.id}
                        disabled
                    />
                </Form.Group>
            )}

            <Form.Group className="mb-3">
                <Form.Label htmlFor="name">Commons Name</Form.Label>
                <Form.Control
                    data-testid={`${testid}-name`}
                    id="name"
                    type="text"
                    isInvalid={!!errors.name}
                    {...register("name", {
                        required: "Commons name is required",
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.name?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="startingBalance">
                    Starting Balance
                </Form.Label>
                <Form.Control
                    id="startingBalance"
                    data-testid={`${testid}-startingBalance`}
                    type="number"
                    step="0.01"
                    defaultValue={10000}
                    isInvalid={!!errors.startingBalance}
                    {...register("startingBalance", {
                        valueAsNumber: true,
                        required: "Starting Balance is required",
                        min: {
                            value: 0.01,
                            message: "Starting Balance must be positive",
                        },
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.startingBalance?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="cowPrice">Cow Price</Form.Label>
                <Form.Control
                    data-testid={`${testid}-cowPrice`}
                    id="cowPrice"
                    type="number"
                    step="0.01"
                    defaultValue={100}
                    isInvalid={!!errors.cowPrice}
                    {...register("cowPrice", {
                        valueAsNumber: true,
                        required: "Cow price is required",
                        min: {
                            value: 0.01,
                            message: "Cow price must be positive",
                        },
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.cowPrice?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="priceChange">Price Change</Form.Label>
                <Form.Control
                    data-testid={`${testid}-priceChange`}
                    id="priceChange"
                    type="number"
                    step="0.01"
                    defaultValue={0.1}
                    isInvalid={!!errors.priceChange}
                    {...register("priceChange", {
                        valueAsNumber: true,
                        required: "Cow price change is required",
                        min: {
                            value: 0.01,
                            message: "Cow price change must be positive",
                        },
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.priceChange?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="milkPrice">Milk Price</Form.Label>
                <Form.Control
                    data-testid={`${testid}-milkPrice`}
                    id="milkPrice"
                    type="number"
                    step="0.01"
                    defaultValue={20}
                    isInvalid={!!errors.milkPrice}
                    {...register("milkPrice", {
                        valueAsNumber: true,
                        required: "Milk price is required",
                        min: {
                            value: 0.01,
                            message: "Milk price must be positive",
                        },
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.milkPrice?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="startingDate">Starting Date</Form.Label>
                <Form.Control
                    data-testid={`${testid}-startingDate`}
                    id="startingDate"
                    type="date"
                    defaultValue={today}
                    isInvalid={!!errors.startingDate}
                    {...register("startingDate", {
                        valueAsDate: true,
                        validate: {
                            isPresent: (v) =>
                                !isNaN(v) || "Starting date is required",
                        },
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.startingDate?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="lastDate">Last Date</Form.Label>
                <Form.Control
                    data-testid={`${testid}-lastDate`}
                    id="lastDate"
                    type="date"
                    defaultValue={onemonthfromtoday}
                    isInvalid={!!errors.lastDate}
                    {...register("lastDate", {
                        valueAsDate: true,
                        validate: {
                            isPresent: (v) =>
                                !isNaN(v) || "Last date is required",
                        },
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.lastDate?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="degradationRate">
                    Degradation Rate
                </Form.Label>
                <Form.Control
                    data-testid={`${testid}-degradationRate`}
                    id="degradationRate"
                    type="number"
                    defaultValue={1}
                    step="0.01"
                    isInvalid={!!errors.degradationRate}
                    {...register("degradationRate", {
                        valueAsNumber: true,
                        required: "Degradation rate is required",
                        min: {
                            value: 0.0,
                            message: "Degradation rate must be positive",
                        },
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.degradationRate?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="carryingCapacity">
                    Carrying Capacity
                </Form.Label>
                <Form.Control
                    data-testid={`${testid}-carryingCapacity`}
                    id="carryingCapacity"
                    type="number"
                    step="1"
                    defaultValue={100}
                    isInvalid={!!errors.carryingCapacity}
                    {...register("carryingCapacity", {
                        valueAsNumber: true,
                        required: "Carrying capacity is required",
                        min: {
                            value: 1,
                            message: "Carrying Capacity must be greater than 0",
                        },
                    })}
                />
                <Form.Control.Feedback type="invalid">
                    {errors.carryingCapacity?.message}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label htmlFor="showLeaderboard">
                    Show Leaderboard?
                </Form.Label>
                <Form.Check
                    data-testid={`${testid}-showLeaderboard`}
                    type="checkbox"
                    id="showLeaderboard"
                    {...register("showLeaderboard")}
                />
            </Form.Group>

            <Button type="submit" data-testid="CommonsForm-Submit-Button">
                {buttonLabel}
            </Button>
        </Form>
    );
}

export default CommonsForm;
